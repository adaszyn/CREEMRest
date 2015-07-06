package energymeter.util;

/**
 * Created by root on 7/6/15.
 */

public class EnergyType {
    public static String getTable(String type) throws Exception {
        switch(type) {
            case "consumed":
                return "t_data_total_active_energy_consumed";
            case "produced":
                return "t_data_total_active_energy_produced";
            case "power":
                return "t_data_total_active_power";
            default:
                throw new Exception("Wrong case argument");
        }
    }
}
