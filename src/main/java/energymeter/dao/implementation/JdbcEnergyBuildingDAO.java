package energymeter.dao.implementation;


import energymeter.dao.EnergyBuildingDAO;
import energymeter.model.BuildingEnergy;
import energymeter.model.EnergyAbstract;
import energymeter.util.EnergyDAOHelper;
import energymeter.util.EnergyFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by root on 7/22/15.
 */
public class JdbcEnergyBuildingDAO implements EnergyBuildingDAO {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ArrayList<BuildingEnergy> getBuildingEnergy() throws SQLException {
        String sql = "\n" +
                "SELECT C.pod, GREATEST( MAX(01_am), MAX(02_am), MAX(03_am), MAX(04_am), MAX(05_am), MAX(06_am), MAX(07_am), MAX(08_am), MAX(09_am), MAX(10_am), MAX(11_am), MAX(12_am), MAX(01_pm), MAX(02_pm), MAX(03_pm), MAX(04_pm), MAX(05_pm), MAX(06_pm), MAX(07_pm), MAX(08_pm), MAX(09_pm), MAX(10_pm), MAX(11_pm), MAX(12_pm) ) AS potenza_picco, \n" +
                "((gen) + (feb) + (mar) + (apr) + (mag) + (giu) + (lug) + (ago) + (sett) + (ott) + (nov) +(dic)) as energia_totale_anno,\n" +
                "((gen) + (feb) + (mar) + (apr) + (mag) + (giu) + (lug) + (ago) + (sett) + (ott) + (nov) +(dic))/superficieUtile as densita_energia_anno\n" +
                "\n" +
                "\n" +
                "FROM dss_immobili as I \n" +
                "inner join dss_consuntivi as C \n" +
                "ON I.pod=C.pod\n" +
                "INNER JOIN dss_datimultiorari as D \n" +
                "ON C.pod=D.pod\n" +
                "\n" +
                "where superficieUtile != 0 and anno='2014'\n" +
                "\n" +
                "GROUP BY C.pod, anno";
        ArrayList<BuildingEnergy> energyResults = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            BuildingEnergy objectType;
            while (rs.next()) {
                objectType = new BuildingEnergy();
                objectType.setPod(rs.getString("pod"));
                objectType.setPotenza_picco(rs.getDouble("potenza_picco"));
                objectType.setDensita_energia_anno(rs.getDouble("densita_energia_anno"));
                objectType.setEnergia_totale_anno(rs.getDouble("energia_totale_anno"));
                energyResults.add(objectType);
            }
            rs.close();
            ps.close();
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
}
