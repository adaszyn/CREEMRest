package energymeter.model;

import java.sql.Timestamp;

/**
 * Created by root on 7/6/15.
 */

public abstract class EnergyAbstract {
    private String id;
    private double value;
    private Timestamp timestamp;
    private boolean isPrediction;

    public EnergyAbstract() {}

    public EnergyAbstract(String id, double value, Timestamp timestamp, boolean isPrediction) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
        this.isPrediction = isPrediction;
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

    public boolean isPrediction() {
        return isPrediction;
    }

    public void setIsPrediction(boolean isPrediction) {
        this.isPrediction = isPrediction;
    }

    public String toString(){
        return String.format("[%d, %f, %s, %b]", getId(), getValue(), getTimestamp().toString(), isPrediction());
    }


}
