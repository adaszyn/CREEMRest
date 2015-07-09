package energymeter.dao.implementation;

/**
 * Created by wojtek on 7/3/15.
 */

import energymeter.model.ConsumedEnergy;
import energymeter.dao.EnergyDAO;
import energymeter.model.EnergyAbstract;
import energymeter.model.ProducedEnergy;
import energymeter.util.EnergyFactory;
import energymeter.util.EnergyTypesEnum;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.ArrayList;


public class JdbcEnergyDAO implements EnergyDAO {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(ConsumedEnergy consumedEnergy) {

    }

    @Override
    public ArrayList<EnergyAbstract> getEnergyById(EnergyTypesEnum type, String id, Integer limit) throws Exception {
        EnergyAbstract objectType;
        String sql = "select * from " + type.getTable() + " WHERE DEVICE_ID = ? LIMIT ?";
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            if(limit == null)
                ps.setInt(2, 10);
            else
                ps.setInt(2, limit);
            ArrayList<EnergyAbstract> energies = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                objectType = EnergyFactory.getEnergyInstance(type);
                objectType.setId(rs.getString("DEVICE_ID"));
                objectType.setValue(rs.getDouble("MEASURE_VALUE"));
                objectType.setTimestamp(rs.getTimestamp("MEASURE_TIMESTAMP"));
                if (type.toString().contains("CONSUMED")) {
                    ((ConsumedEnergy) objectType).setDelta(rs.getDouble("MEASURE_V_DELTA"));
                }
                else if (type.toString().contains("PRODUCED")) {
                    ((ProducedEnergy) objectType).setDelta(rs.getDouble("MEASURE_V_DELTA"));
                }
                energies.add(objectType);
            }
            rs.close();
            ps.close();
            return energies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
    }
    @Override
    public ArrayList<EnergyAbstract> getAllEnergy(EnergyTypesEnum type, Integer limit) throws Exception {
        EnergyAbstract objectType;
        String sql = "select * from " + type.getTable() + " LIMIT ?";
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            if(limit == null)
                ps.setInt(1, 10);
            else
                ps.setInt(1, limit);
            ArrayList<EnergyAbstract> energyAbstractPortions = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                objectType = EnergyFactory.getEnergyInstance(type);
                objectType.setId(rs.getString("DEVICE_ID"));
                objectType.setValue(rs.getDouble("MEASURE_VALUE"));
                objectType.setTimestamp(rs.getTimestamp("MEASURE_TIMESTAMP"));
                if (type.toString().contains("CONSUMED")) {
                    ((ConsumedEnergy) objectType).setDelta(rs.getDouble("MEASURE_V_DELTA"));
                }
                else if (type.toString().contains("PRODUCED")) {
                    ((ProducedEnergy) objectType).setDelta(rs.getDouble("MEASURE_V_DELTA"));
                }
                energyAbstractPortions.add(objectType);
            }
            rs.close();
            ps.close();
            return energyAbstractPortions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public ArrayList<EnergyAbstract> getEnergyByIdDate(EnergyTypesEnum type, String id, Date date, Integer limit) throws Exception {
        EnergyAbstract objectType;
        String sql = "select * from " + type.getTable() + " where DEVICE_ID = ? and DATE(MEASURE_TIMESTAMP) = ? LIMIT ?";
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            ps.setString(1, id);
            ps.setDate(2, sqlDate);
            if(limit == null)
                ps.setInt(3, 10);
            else
                ps.setInt(3, limit);
            ArrayList<EnergyAbstract> energyAbstractPortions = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                objectType = EnergyFactory.getEnergyInstance(type);
                objectType.setId(rs.getString("DEVICE_ID"));
                objectType.setValue(rs.getDouble("MEASURE_VALUE"));
                objectType.setTimestamp(rs.getTimestamp("MEASURE_TIMESTAMP"));
                if (type.toString().contains("CONSUMED")) {
                    ((ConsumedEnergy) objectType).setDelta(rs.getDouble("MEASURE_V_DELTA"));
                }
                else if (type.toString().contains("PRODUCED")) {
                    ((ProducedEnergy) objectType).setDelta(rs.getDouble("MEASURE_V_DELTA"));
                }
                energyAbstractPortions.add(objectType);
            }
            rs.close();
            ps.close();
            return energyAbstractPortions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public ArrayList<EnergyAbstract> getEnergyByIdDates(EnergyTypesEnum type, String id, Date dateFrom, Date dateTo, Integer limit) throws Exception {
        EnergyAbstract objectType;
        String sql = "select * from " + type.getTable() + " where DEVICE_ID = ? and DATE(MEASURE_TIMESTAMP) >= ? and DATE(MEASURE_TIMESTAMP) <= ? LIMIT ?";
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            java.sql.Date sqlDateFrom = new java.sql.Date(dateFrom.getTime());
            java.sql.Date sqlDateTo = new java.sql.Date(dateTo.getTime());
            ps.setString(1, id);
            ps.setDate(2, sqlDateFrom);
            ps.setDate(3, sqlDateTo);
            if(limit == null)
                ps.setInt(4, 10);
            else
                ps.setInt(4, limit);
            ArrayList<EnergyAbstract> energyAbstractPortions = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                objectType = EnergyFactory.getEnergyInstance(type);
                objectType.setId(rs.getString("DEVICE_ID"));
                objectType.setValue(rs.getDouble("MEASURE_VALUE"));
                objectType.setTimestamp(rs.getTimestamp("MEASURE_TIMESTAMP"));
                if (type.toString().contains("CONSUMED")) {
                    ((ConsumedEnergy) objectType).setDelta(rs.getDouble("MEASURE_V_DELTA"));
                }
                else if (type.toString().contains("PRODUCED")) {
                    ((ProducedEnergy) objectType).setDelta(rs.getDouble("MEASURE_V_DELTA"));
                }
                energyAbstractPortions.add(objectType);
            }
            rs.close();
            ps.close();
            return energyAbstractPortions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public ArrayList<EnergyAbstract> getMeters() throws Exception {
        EnergyAbstract objectType;
        String sql = "select distinct DEVICE_ID, MEASURE_VALUE, MEASURE_TIMESTAMP from t_data_latest where MEASURE_TYPE='active_power' or MEASURE_TYPE='total_active_power'";
        Connection connection = null;

        try {
            connection = dataSource.getConnection();

            ArrayList<EnergyAbstract> meters = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                objectType = EnergyFactory.getEnergyInstance(EnergyTypesEnum.ACTIVE_POWER_1);
                objectType.setId(rs.getString("DEVICE_ID"));
                objectType.setValue(rs.getDouble("MEASURE_VALUE"));
                objectType.setTimestamp(rs.getTimestamp("MEASURE_TIMESTAMP"));
                meters.add(objectType);
            }
            rs.close();
            ps.close();
            return meters;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
    }

}

