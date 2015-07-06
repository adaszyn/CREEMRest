package energymeter.dao;

import energymeter.model.ConsumedEnergy;

import java.util.Date;
import java.util.ArrayList;

/**
 * Created by wojtek on 7/3/15.
 */
public interface ConsumedEnergyDAO {
    public void insert(ConsumedEnergy consumedEnergy);
    public ArrayList<ConsumedEnergy> getConsumedEnergyById(int id, Integer limit);
    public ArrayList<ConsumedEnergy> getAllConsumedEnergy(Integer limit);
    public ArrayList<ConsumedEnergy> getConsumedEnergyByIdDate(int id, Date date, Integer limit);
}
