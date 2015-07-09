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

    public ArrayList<EnergyAbstract> predict(String deviceID) throws Exception {
        String sql = "select * from t_data_total_active_power where DEVICE_ID = ? limit 10";
        EnergyAbstract objectType;
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            ArrayList<EnergyAbstract> pastResults = new ArrayList<>();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, deviceID);
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

            long oneDay = 60*60*24*1000;
            double valueDif = pastResults.get(pastResults.size() - 1).getValue() - pastResults.get(0).getValue();
            long timeDif = pastResults.get(pastResults.size()-1).getTimestamp().getTime() - pastResults.get(0).getTimestamp().getTime();
            double perDay = oneDay / (float)timeDif;
            double valuePerDay = valueDif*perDay;
            double maxValue = pastResults.get(pastResults.size() - 1).getValue();
            Timestamp now = new Timestamp(new java.util.Date().getTime()+oneDay);
            ArrayList<EnergyAbstract> futureResults = new ArrayList<>();
            //predictions for week
            for (int i=0;i<7;i++) {
                maxValue += valuePerDay;
                now = new Timestamp(now.getTime()+oneDay);
                futureResults.add(new ConsumedEnergy(deviceID, maxValue, valuePerDay, now));
            }

            return futureResults;
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
