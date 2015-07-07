package energymeter.dao;

import energymeter.model.ConsumedEnergy;
import energymeter.model.EnergyAbstract;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by wojtek on 7/3/15.
 */
public interface EnergyDAO {
    public void insert(ConsumedEnergy consumedEnergy);
    public ArrayList<EnergyAbstract> getEnergyById(String type, int id, Integer limit) throws Exception;
    public ArrayList<EnergyAbstract> getAllEnergy(String type, Integer limit) throws Exception;
    public ArrayList<EnergyAbstract> getEnergyByIdDate(String type, int id, Date date, Integer limit) throws Exception;
}
