package energymeter.dao;

import energymeter.model.ConsumedEnergy;
import energymeter.model.Energy;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by wojtek on 7/3/15.
 */
public interface EnergyDAO {
    public void insert(ConsumedEnergy consumedEnergy);
    public ArrayList<Energy> getEnergyById(String type, int id, Integer limit) throws Exception;
    public ArrayList<Energy> getAllEnergy(String type, Integer limit) throws Exception;
    public ArrayList<Energy> getEnergyByIdDate(String type, int id, Date date, Integer limit) throws Exception;
}
