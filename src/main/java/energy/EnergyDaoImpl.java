package energy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class EnergyDaoImpl implements EnergyDao {
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public ArrayList<EnergyData> getEnergy() {
        String sql="select DEVICE_ID, MEASURE_TIMESTAMP, MEASURE_VALUE from t_data_total_active_energy_consumed LIMIT 1";
        jdbcTemplate = new JdbcTemplate(dataSource);

        ArrayList<EnergyData> data = new ArrayList<EnergyData>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            EnergyData energy = new EnergyData();
            energy.setDeviceID(Integer.parseInt(String.valueOf(row.get("DEVICE_ID"))));
            energy.setEnergy_measure(Integer.parseInt(String.valueOf(row.get("MEASURE_VALUE"))));
            energy.setDate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(row.get("MEASURE_TIMESTAMP")));
            data.add(energy);
        }
        return data;
    }

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource=dataSource;
    }
}
