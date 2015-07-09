package energymeter.dao;

import energymeter.model.EnergyAbstract;

import java.util.ArrayList;

/**
 * Created by root on 7/8/15.
 */
public interface PredictionDAO {
    public ArrayList<EnergyAbstract> predict(String deviceID) throws Exception;
}
