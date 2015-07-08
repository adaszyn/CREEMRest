package energymeter.util;

/**
 * Created by wojtek on 7/7/15.
 */
public enum EnergyTypesEnum {
    ACTIVE_CONSUMED_1("t_data_active_energy_1_consumed"),
    ACTIVE_CONSUMED_2("t_data_active_energy_2_consumed"),
    ACTIVE_CONSUMED_3("t_data_active_energy_3_consumed"),
    ACTIVE_PRODUCED_1("t_data_active_energy_1_produced"),
    ACTIVE_PRODUCED_2("t_data_active_energy_2_produced"),
    ACTIVE_PRODUCED_3("t_data_active_energy_3_produced"),
    ACTIVE_POWER_1("t_data_active_power_1"),
    ACTIVE_POWER_2("t_data_active_power_2"),
    ACTIVE_POWER_3("t_data_active_power_3"),
    REACTIVE_CONSUMED_1("t_data_reactive_energy_1_consumed"),
    REACTIVE_CONSUMED_2("t_data_reactive_energy_2_consumed"),
    REACTIVE_CONSUMED_3("t_data_reactive_energy_3_consumed"),
    REACTIVE_PRODUCED_1("t_data_reactive_energy_1_produced"),
    REACTIVE_PRODUCED_2("t_data_reactive_energy_2_produced"),
    REACTIVE_PRODUCED_3("t_data_reactive_energy_3_produced"),
    REACTIVE_POWER_1("t_data_reactive_power_1"),
    REACTIVE_POWER_2("t_data_reactive_power_2"),
    REACTIVE_POWER_3("t_data_reactive_power_3"),
    TOTAL_ACTIVE_CONSUMED("t_data_total_active_energy_consumed"),
    TOTAL_ACTIVE_PRODUCED("t_data_total_active_energy_produced"),
    TOTAL_ACTIVE_POWER("t_data_total_active_power"),
    TOTAL_REACTIVE_CONSUMED("t_data_total_reactive_energy_consumed"),
    TOTAL_REACTIVE_PRODUCED("t_data_total_reactive_energy_produced"),
    TOTAL_REACTIVE_POWER("t_data_total_reactive_power"),

    LATEST("t_data_latest");

    private String table;
    EnergyTypesEnum(String table) {
        this.table=table;
    }

    public String getTable() {
        return this.table;
    }
}
