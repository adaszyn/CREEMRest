package energymeter.dao.implementation;

/**
 * Created by wojtek on 7/3/15.
 */

import energymeter.model.ConsumedEnergy;
import energymeter.dao.ConsumedEnergyDAO;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.ArrayList;


public class JdbcConsumedEnergyDAO implements ConsumedEnergyDAO {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(ConsumedEnergy consumedEnergy) {

    }

    @Override
    public ArrayList<ConsumedEnergy> getConsumedEnergyById(int id, Integer limit) {
        String sql = "select * from  t_data_total_active_energy_consumed WHERE DEVICE_ID = ? LIMIT ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            if(limit == null)
                ps.setInt(2, 10);
            else
                ps.setInt(2, limit);
            ConsumedEnergy consumedEnergy = null;
            ArrayList<ConsumedEnergy> consumedEnergies = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                consumedEnergies.add(new ConsumedEnergy(
                        rs.getInt("DEVICE_ID"),
                        rs.getDouble("MEASURE_VALUE"),
                        rs.getDouble("MEASURE_V_DELTA"),
                        rs.getTimestamp("MEASURE_TIMESTAMP")
                ));
            }
            rs.close();
            ps.close();
            return consumedEnergies;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
    @Override
    public ArrayList<ConsumedEnergy> getAllConsumedEnergy(Integer limit){
        String sql = "select * from  t_data_total_active_energy_consumed LIMIT ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            if(limit == null)
                ps.setInt(1, 10);
            else
                ps.setInt(1, limit);
            ArrayList<ConsumedEnergy> consumedEnergyPortions = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                consumedEnergyPortions.add(new ConsumedEnergy(
                        rs.getInt("DEVICE_ID"),
                        rs.getDouble("MEASURE_VALUE"),
                        rs.getDouble("MEASURE_V_DELTA"),
                        rs.getTimestamp("MEASURE_TIMESTAMP")
                ));
            }
            rs.close();
            ps.close();
            return consumedEnergyPortions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    @Override
    public ArrayList<ConsumedEnergy> getConsumedEnergyByIdDate(int id, Date date, Integer limit) {

        String sql = "select * from  t_data_total_active_energy_consumed where DEVICE_ID = ? and DATE(MEASURE_TIMESTAMP) = ? LIMIT ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            Timestamp timestamp = new Timestamp(date.getTime());
            ps.setInt(1, id);
            ps.setDate(2, sqlDate);
            if(limit == null)
                ps.setInt(3, 10);
            else
                ps.setInt(3, limit);
            ArrayList<ConsumedEnergy> consumedEnergyPortions = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                consumedEnergyPortions.add(new ConsumedEnergy(
                        rs.getInt("DEVICE_ID"),
                        rs.getDouble("MEASURE_VALUE"),
                        rs.getDouble("MEASURE_V_DELTA"),
                        rs.getTimestamp("MEASURE_TIMESTAMP")
                ));
            }
            rs.close();
            ps.close();
            return consumedEnergyPortions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

}

