package energymeter.dao;

import energymeter.model.ConsumedEnergy;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by wojtek on 7/3/15.
 */
public interface ConsumedEnergyDAO {
    public void insert(ConsumedEnergy consumedEnergy);
    public ArrayList<ConsumedEnergy> getConsumedEnergyById(int id);
    public ArrayList<ConsumedEnergy> getAllConsumedEnergy();
    public ArrayList<ConsumedEnergy> getConsumedEnergyByIdDate(int id, Date date);
}
