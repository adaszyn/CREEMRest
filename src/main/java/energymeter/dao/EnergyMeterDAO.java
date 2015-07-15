package energymeter.dao;

import energymeter.model.EnergyAbstract;
import energymeter.util.EnergyTypesEnum;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 7/10/15.
 */
public interface EnergyMeterDAO {
    public ArrayList<EnergyAbstract> getEnergyDay(EnergyTypesEnum typeEnum, String deviceID, Date date) throws Exception;
    public ArrayList<EnergyAbstract> getEnergyPeriod(EnergyTypesEnum typeEnum, String deviceID, Date dateFrom, Date dateTo) throws Exception;
    public ArrayList<EnergyAbstract> getLatest(String deviceID) throws Exception;
    public ArrayList<EnergyAbstract> getLatestDate(Date date) throws Exception;
}
