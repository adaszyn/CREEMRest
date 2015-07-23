package energymeter.dao;

import energymeter.model.BuildingEnergy;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by root on 7/22/15.
 */

public interface EnergyBuildingDAO {
    public ArrayList<BuildingEnergy> getBuildingEnergy() throws SQLException;
}
