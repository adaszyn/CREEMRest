package energymeter.dao.implementation;

/**
 * Created by wojtek on 7/3/15.
 */

import energymeter.model.ConsumedEnergy;
import energymeter.dao.EnergyDAO;
import energymeter.model.EnergyAbstract;
import energymeter.model.ProducedEnergy;
import energymeter.util.EnergyFactory;
import energymeter.util.EnergyTableNameFactory;

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
    public ArrayList<EnergyAbstract> getEnergyById(String type, int id, Integer limit) throws Exception {
        EnergyAbstract objectType;
        String table = EnergyTableNameFactory.getTable(type);
        String sql = "select * from " + table + " WHERE DEVICE_ID = ? LIMIT ?";
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            if(limit == null)
                ps.setInt(2, 10);
            else
                ps.setInt(2, limit);
            ArrayList<EnergyAbstract> energies = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                objectType = EnergyFactory.getEnergyInstance(type);
                objectType.setId(rs.getInt("DEVICE_ID"));
                objectType.setValue(rs.getDouble("MEASURE_VALUE"));
                objectType.setTimestamp(rs.getTimestamp("MEASURE_TIMESTAMP"));
                if (type.equals("consumed")) {
                    ((ConsumedEnergy) objectType).setDelta(rs.getDouble("MEASURE_V_DELTA"));
                }
                else if (type.equals("produced")) {
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
    public ArrayList<EnergyAbstract> getAllEnergy(String type, Integer limit) throws Exception {
        EnergyAbstract objectType;
        String table = EnergyTableNameFactory.getTable(type);
        String sql = "select * from " + table + " LIMIT ?";
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
                objectType.setId(rs.getInt("DEVICE_ID"));
                objectType.setValue(rs.getDouble("MEASURE_VALUE"));
                objectType.setTimestamp(rs.getTimestamp("MEASURE_TIMESTAMP"));
                if (type.equals("consumed")) {
                    ((ConsumedEnergy) objectType).setDelta(rs.getDouble("MEASURE_V_DELTA"));
                }
                else if (type.equals("produced")) {
                    ((ProducedEnergy) objectType).setDelta(rs.getDouble("MEASURE_V_DELTA"));
                }
                objectType.setId(rs.getInt("DEVICE_ID"));
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
    public ArrayList<EnergyAbstract> getEnergyByIdDate(String type, int id, Date date, Integer limit) throws Exception {
        EnergyAbstract objectType;
        String table = EnergyTableNameFactory.getTable(type);
        String sql = "select * from " + table + " where DEVICE_ID = ? and DATE(MEASURE_TIMESTAMP) = ? LIMIT ?";
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            ps.setInt(1, id);
            ps.setDate(2, sqlDate);
            if(limit == null)
                ps.setInt(3, 10);
            else
                ps.setInt(3, limit);
            ArrayList<EnergyAbstract> energyAbstractPortions = new ArrayList<>();
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                objectType = EnergyFactory.getEnergyInstance(type);
                objectType.setId(rs.getInt("DEVICE_ID"));
                objectType.setValue(rs.getDouble("MEASURE_VALUE"));
                objectType.setTimestamp(rs.getTimestamp("MEASURE_TIMESTAMP"));
                if (type.equals("consumed")) {
                    ((ConsumedEnergy) objectType).setDelta(rs.getDouble("MEASURE_V_DELTA"));
                }
                else if (type.equals("produced")) {
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

}

