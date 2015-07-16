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
                for (EnergyAbstract en:energyResults) {
                    Date tmpdate = new Date(en.getTimestamp().getTime());
                    DateFormat formatter = new SimpleDateFormat("HH");
                    String dateFormatted = formatter.format(tmpdate);
                    hours.get(Integer.parseInt(dateFormatted)).add(en.getValue());
                }

                //time -2hours because without it it starts from 2 A.M.
                //Delta is always valuePerHour even if there are already deltas from database
                Timestamp timeThen = new Timestamp(sqlDate.getTime());
                energyDay = EnergyDAOHelper.getFinalResults(timeThen, type, hours, id, energyDay, valuePerHour, oneHour, 24);
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
                catch (SQLException e) {}
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
            //roznica danego okresu w dniach
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
                for (EnergyAbstract en:energyResults) {
                    long tmpDate = en.getTimestamp().getTime() - dateFrom.getTime();
                    tmpDate = TimeUnit.DAYS.convert(tmpDate, TimeUnit.MILLISECONDS);
                    days.get((int)tmpDate).add(en.getValue());
                }

                //whichever time of the day, here - 12 A.M.
                //Delta is always valuePerDay even if there are already deltas from database
                Timestamp timeThen = new Timestamp(sqlDateFrom.getTime() + (12 * oneHour));
                EnergyDAOHelper.getFinalResults(timeThen, type, days, id, energyPeriod, valuePerDay, oneDay, dayDiff);
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
                catch (SQLException e) {}
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
                catch (SQLException e) {}
            }
            return energyResults;
        }
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
                catch (SQLException e) {}
            }
            return energyResults;
        }
    }
}
