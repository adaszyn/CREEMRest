package energymeter.util;

import energymeter.model.ConsumedEnergy;
import energymeter.model.EnergyAbstract;
import energymeter.model.Power;
import energymeter.model.ProducedEnergy;

/**
 * Created by root on 7/6/15.
 */

public class EnergyFactory {
    public static EnergyAbstract getEnergyInstance(EnergyTypesEnum type) throws Exception {
        if (type.toString().contains("CONSUMED"))
            return new ConsumedEnergy();
        else if (type.toString().contains("PRODUCED"))
            return new ProducedEnergy();
        else if (type.toString().contains("POWER"))
            return new Power();
        else
            throw new Exception("Wrong case argument");
    }
}
