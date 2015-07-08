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
        switch (type) {
            case TOTAL_ACTIVE_CONSUMED:
                return new ConsumedEnergy();
            case TOTAL_ACTIVE_PRODUCED:
                return new ProducedEnergy();
            case TOTAL_ACTIVE_POWER:
                return new Power();
            default:
                throw new Exception("Wrong case argument");
        }
    }
}
