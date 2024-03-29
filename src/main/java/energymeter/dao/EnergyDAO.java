package energymeter.dao;

import energymeter.model.ConsumedEnergy;
import energymeter.model.EnergyAbstract;
import energymeter.util.EnergyTypesEnum;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by wojtek on 7/3/15.
 */
public interface EnergyDAO {
    public void insert(ConsumedEnergy consumedEnergy);
    public ArrayList<EnergyAbstract> getEnergyById(EnergyTypesEnum type, String id, Integer limit) throws Exception;
    public ArrayList<EnergyAbstract> getAllEnergy(EnergyTypesEnum type, Integer limit) throws Exception;
    public ArrayList<EnergyAbstract> getEnergyByIdDate(EnergyTypesEnum type, String id, Date date, Integer limit) throws Exception;
    public ArrayList<EnergyAbstract> getEnergyByIdDates(EnergyTypesEnum type, String id, Date dateFrom, Date dateTo, Integer limit) throws Exception;
    public ArrayList<EnergyAbstract> getMeters() throws Exception;
}
