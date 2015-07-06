package energymeter.model;

/**
 * Created by root on 7/6/15.
 */

public class EnergyFactory {
    public static Energy getEnergyInstance(String type) throws Exception {
        switch (type) {
            case "consumed":
                return new ConsumedEnergy();
            case "produced":
                return new ProducedEnergy();
            case "power":
                return new Power();
            default:
                throw new Exception("Wrong case argument");
        }
    }
}
