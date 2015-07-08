package energymeter.model;

import java.sql.Timestamp;

/**
 * Created by root on 7/6/15.
 */

public abstract class EnergyAbstract {
    private String id;
    private double value;
    private Timestamp timestamp;

    public EnergyAbstract() {}

    public EnergyAbstract(String id, double value, Timestamp timestamp) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String toString(){
        return String.format("[%d, %f, %s]", getId(), getValue(), getTimestamp().toString());
    }


}
