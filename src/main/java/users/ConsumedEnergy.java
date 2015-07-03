package users;
import java.sql.Timestamp;
/**
 * Created by wojtek on 7/3/15.
 */
public class ConsumedEnergy {
    private int id;
    private double value;
    private double delta;
    private Timestamp timestamp;

    public ConsumedEnergy(int id, double value, double delta, Timestamp timestamp) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
        this.delta = delta;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String toString(){
        return Double.toString(value);
    }
}
