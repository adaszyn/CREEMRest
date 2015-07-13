package energymeter.dao.implementation;

import energymeter.dao.PredictionDAO;
import energymeter.model.ConsumedEnergy;
import energymeter.model.EnergyAbstract;
import energymeter.util.EnergyFactory;
import energymeter.util.EnergyTypesEnum;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by root on 7/8/15.
 */
public class JdbcPredictionDAO implements PredictionDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<EnergyAbstract> predict(String deviceID, Integer days, Integer limit) throws Exception {
        String sql = "select * from t_data_total_active_energy_consumed where DEVICE_ID = ? order by MEASURE_TIMESTAMP limit ?";
        EnergyAbstract objectType;
        Connection connection = null;
        ArrayList<EnergyAbstract> pastResults = new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, deviceID);
            if(limit == null)
                ps.setInt(2, 10);
            else
                ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                objectType = EnergyFactory.getEnergyInstance(EnergyTypesEnum.TOTAL_ACTIVE_CONSUMED);
                objectType.setId(rs.getString("DEVICE_ID"));
                objectType.setValue(rs.getDouble("MEASURE_VALUE"));
                objectType.setTimestamp(rs.getTimestamp("MEASURE_TIMESTAMP"));
                pastResults.add(objectType);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
            ArrayList<EnergyAbstract> futureResults = new ArrayList<>();
            if (pastResults.size()>1) {
                long oneDay = 60 * 60 * 24 * 1000;
                double valueDif = pastResults.get(pastResults.size() - 1).getValue() - pastResults.get(0).getValue();
                long timeDif = pastResults.get(pastResults.size() - 1).getTimestamp().getTime() - pastResults.get(0).getTimestamp().getTime();
                double perDay = oneDay / (double) timeDif;
                double valuePerDay = valueDif * perDay;
                double maxValue = pastResults.get(pastResults.size() - 1).getValue();
                Timestamp now = new Timestamp(new java.util.Date().getTime() + oneDay);

                long nowLastResultTimeDif = new java.util.Date().getTime() - pastResults.get(pastResults.size() - 1).getTimestamp().getTime();
                maxValue += ((double) nowLastResultTimeDif / oneDay) * valuePerDay;

                //predictions for days
                if (days == null)
                    days = 7;
                for (int i = 0; i < days; i++) {
                    maxValue += valuePerDay;
                    now = new Timestamp(now.getTime() + oneDay);
                    futureResults.add(new ConsumedEnergy(deviceID, maxValue, valuePerDay, now, true));
                }
            }

            return futureResults;
        }
    }
}
