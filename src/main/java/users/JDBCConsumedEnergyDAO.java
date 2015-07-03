package users;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wojtek on 7/3/15.
 */
public class JDBCConsumedEnergyDAO implements ConsumedEnergyDAO{
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void insert(ConsumedEnergy consumedEnergy) {

    }

    @Override
    public ConsumedEnergy getConsumedEnergyById(int id) {
        String sql = "select * from  t_data_total_active_energy_consumed WHERE DEVICE_ID = ? LIMIT 1";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ConsumedEnergy consumedEnergy = null;
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                consumedEnergy = new ConsumedEnergy(
                        rs.getInt("DEVICE_ID"),
                        rs.getDouble("MEASURE_VALUE"),
                        rs.getDouble("MEASURE_V_DELTA"),
                        rs.getTimestamp("MEASURE_TIMESTAMP")
                );
            }
            rs.close();
            ps.close();
            return consumedEnergy;
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
