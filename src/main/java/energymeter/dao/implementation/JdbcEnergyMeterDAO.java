package energymeter.dao.implementation;

import energymeter.dao.EnergyMeterDAO;
import energymeter.model.EnergyAbstract;
import energymeter.util.EnergyDAOHelper;
import energymeter.util.EnergyTypesEnum;

import javax.sql.DataSource;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 7/10/15.
 */

public class JdbcEnergyMeterDAO implements EnergyMeterDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public ArrayList<EnergyAbstract> getEnergyDay(EnergyTypesEnum type, String id, Date date) throws Exception {
        String sql = "select * from " + type.getTable() + " where DEVICE_ID = ? and DATE(MEASURE_TIMESTAMP) = ? order by MEASURE_TIMESTAMP";
        Connection connection = null;
        ArrayList<EnergyAbstract> energyResults;
        ArrayList<ArrayList<Double>> hours = new ArrayList<>();
        for (int i=0; i<24; i++) {
            hours.add(new ArrayList<>());
        }
        ArrayList<EnergyAbstract> energyDay = new ArrayList<>();
        java.sql.Date sqlDate;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            sqlDate = new java.sql.Date(date.getTime());
            ps.setString(1, id);
            ps.setDate(2, sqlDate);

            energyResults = EnergyDAOHelper.getData(ps, type);

            if (energyResults.size()>1) {
                //calculate time and value differences per hour
                long oneHour = 60 * 60 * 1000;
                double valuePerHour = EnergyDAOHelper.getValuePerTime(energyResults, oneHour);

                //sort values throught the period by hours (adding value to correct ArrayList
                //in ArrayList of ArrayLists - every ArrayList has value from one period of time - one hour)
                EnergyDAOHelper.arrangeData(energyResults, date, TimeUnit.HOURS, hours);

                //Delta is always valuePerHour even if there are already deltas from database
                Timestamp timeThen = new Timestamp(sqlDate.getTime());
                energyDay = EnergyDAOHelper.getFinalResults(timeThen, type, hours, id, valuePerHour, oneHour, 24);
            }
        }
        catch (SQLException e) {
            throw new SQLException(e);
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException ignored) {}
            }
        }
        return energyDay;
    }

    @Override
    public ArrayList<EnergyAbstract> getEnergyPeriod(EnergyTypesEnum type, String id, Date dateFrom, Date dateTo) throws Exception {
        String sql = "select * from " + type.getTable() + " where DEVICE_ID = ? and (DATE(MEASURE_TIMESTAMP) >= ? and DATE(MEASURE_TIMESTAMP) <= ?) order by MEASURE_TIMESTAMP";
        Connection connection = null;
        ArrayList<EnergyAbstract> energyResults;
        ArrayList<ArrayList<Double>> days = new ArrayList<>();

        ArrayList<EnergyAbstract> energyPeriod = new ArrayList<>();
        java.sql.Date sqlDateFrom;
        java.sql.Date sqlDateTo;

        try {
            //difference in days of this period
            long dayDiff = dateTo.getTime() - dateFrom.getTime();
            dayDiff = TimeUnit.DAYS.convert(dayDiff, TimeUnit.MILLISECONDS) + 1;
            for (int i=0; i<dayDiff; i++) {
                days.add(new ArrayList<>());
            }

            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            sqlDateFrom = new java.sql.Date(dateFrom.getTime());
            sqlDateTo = new java.sql.Date(dateTo.getTime());
            ps.setString(1, id);
            ps.setDate(2, sqlDateFrom);
            ps.setDate(3, sqlDateTo);

            energyResults = EnergyDAOHelper.getData(ps, type);

            if (energyResults.size()>1) {
                //calculate time and value differences per hour
                long oneHour = 60 * 60 * 1000;
                long oneDay = oneHour * 24;
                double valuePerDay = EnergyDAOHelper.getValuePerTime(energyResults, oneDay);

                //sort values throught the period by days (adding value to correct ArrayList
                //in ArrayList of ArrayLists - every ArrayList has value from one period of time - one day)
                EnergyDAOHelper.arrangeData(energyResults, dateFrom, TimeUnit.DAYS, days);

                //whichever time of the day, here - 12 A.M.
                //Delta is always valuePerDay even if there are already deltas from database
                Timestamp timeThen = new Timestamp(sqlDateFrom.getTime() + (12 * oneHour));
                energyPeriod = EnergyDAOHelper.getFinalResults(timeThen, type, days, id, valuePerDay, oneDay, dayDiff);
            }
        }
        catch (SQLException e) {
            throw new SQLException(e);
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException ignored) {}
            }
        }
        return energyPeriod;
    }

    @Override
    public ArrayList<EnergyAbstract> getLatest(String id) throws Exception {
        String sql = "select * from t_data_latest where DEVICE_ID = ?";
        ArrayList<EnergyAbstract> energyResults = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);

            energyResults = EnergyDAOHelper.getLatestData(ps);
        }
        catch (SQLException e) {
            throw new SQLException(e);
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException ignored) {}
            }
        }
        return energyResults;
    }

    @Override
    public ArrayList<EnergyAbstract> getLatestDate(Date date) throws Exception {
        String sql = "select * from t_data_latest where DATE(MEASURE_TIMESTAMP)=?";
        ArrayList<EnergyAbstract> energyResults = new ArrayList<>();
        Connection connection = null;
        java.sql.Date sqlDate;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            sqlDate = new java.sql.Date(date.getTime());
            ps.setDate(1, sqlDate);

            energyResults = EnergyDAOHelper.getLatestData(ps);
        }
        catch (SQLException e) {
            throw new SQLException(e);
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException ignored) {}
            }
        }
        return energyResults;
    }

    @Override
    public ArrayList<EnergyAbstract> getEnergyPower(Long step, String id, Date dateFrom, Date dateTo) throws Exception {
        String sql = "select * from t_data_total_active_power where DEVICE_ID = ? and (DATE(MEASURE_TIMESTAMP) >= ? and DATE(MEASURE_TIMESTAMP) <= ?) order by MEASURE_TIMESTAMP";
        Connection connection = null;
        ArrayList<EnergyAbstract> energyResults, lastEnergyResults;
        ArrayList<ArrayList<Double>> periods = new ArrayList<>();
        ArrayList<ArrayList<Double>> lastPeriods = new ArrayList<>();

        ArrayList<EnergyAbstract> energyPeriod = new ArrayList<>();
        ArrayList<EnergyAbstract> lastEnergyPeriod = new ArrayList<>();
        java.sql.Date sqlDateFrom, sqlLastDateFrom;
        java.sql.Date sqlDateTo, sqlLastDateTo;

        long hour = 1000 * 60 * 60;
        long day = hour * 24;
        long week = day * 7;
        TimeUnit unit = null;
        if (step == hour) {
            unit = TimeUnit.HOURS;
        }
        else if (step == day) {
            unit = TimeUnit.DAYS;
        }

        try {
            //difference in time in this period w steps
            long timeDiff = dateTo.getTime() - dateFrom.getTime();
            timeDiff = unit.convert(timeDiff, TimeUnit.MILLISECONDS) + (day / step);
            for (int i=0; i<timeDiff; i++) {
                periods.add(new ArrayList<>());
                lastPeriods.add(new ArrayList<>());
            }
            int predOffset=7;
            if (TimeUnit.DAYS.convert(dateTo.getTime() - dateFrom.getTime(), TimeUnit.MILLISECONDS) + 1 > 27) {
                predOffset=28;
            }

            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            sqlDateFrom = new java.sql.Date(dateFrom.getTime());
            sqlDateTo = new java.sql.Date(dateTo.getTime());
            ps.setString(1, id);
            ps.setDate(2, sqlDateFrom);
            ps.setDate(3, sqlDateTo);
            energyResults = EnergyDAOHelper.getData(ps, EnergyTypesEnum.TOTAL_ACTIVE_POWER);

            ps = connection.prepareStatement(sql);
            sqlLastDateFrom = new java.sql.Date(dateFrom.getTime() - predOffset * day);
            sqlLastDateTo = new java.sql.Date(dateTo.getTime() - predOffset * day);
            Date lastDateFrom = new Date(dateFrom.getTime() - predOffset * day);
            ps.setString(1, id);
            ps.setDate(2, sqlLastDateFrom);
            ps.setDate(3, sqlLastDateTo);
            lastEnergyResults = EnergyDAOHelper.getData(ps, EnergyTypesEnum.TOTAL_ACTIVE_POWER);

            if (energyResults.size()>0) {
                //sort values throught the period by days (adding value to correct ArrayList
                //in ArrayList of ArrayLists - every ArrayList has value from one period of time - one day)
                EnergyDAOHelper.arrangeData(energyResults, dateFrom, unit, periods);
                EnergyDAOHelper.arrangeData(lastEnergyResults, lastDateFrom, unit, lastPeriods);

                Timestamp timeThen = new Timestamp(sqlDateFrom.getTime());
                lastEnergyPeriod = EnergyDAOHelper.getFinalResults(timeThen, EnergyTypesEnum.TOTAL_ACTIVE_POWER, lastPeriods, id, 0.0, step, timeDiff);
                energyPeriod = EnergyDAOHelper.getPowerResults(timeThen, periods, id, step, timeDiff, lastEnergyPeriod);
            }
        }
        catch (SQLException e) {
            throw new SQLException(e);
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                }
                catch (SQLException ignored) {}
            }
        }
        return energyPeriod;
    }
}
