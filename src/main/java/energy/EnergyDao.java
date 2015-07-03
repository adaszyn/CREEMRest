package energy;

import java.util.ArrayList;

public interface EnergyDao {
    ArrayList<EnergyData> getEnergy();

    void setDataSource(org.springframework.jdbc.datasource.DriverManagerDataSource dataSource);
}
