package energymeter.util;

/**
 * Created by wojtek on 7/7/15.
 */
public enum EnergyTypesEnum {
    CONSUMED("t_data_total_active_energy_consumed"),
    PRODUCED("t_data_total_active_energy_produced"),
    POWER("t_data_total_active_power");

    private String table;
    EnergyTypesEnum(String table) {
        this.table=table;
    }

    public String getTable() {
        return this.table;
    }
}
