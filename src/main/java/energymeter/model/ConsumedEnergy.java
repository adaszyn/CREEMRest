package energymeter.model;
import java.sql.Timestamp;

/**
 * Created by root on 7/6/15.
 */

public class ConsumedEnergy extends EnergyAbstract {

    private double delta;

    public ConsumedEnergy() {}

    public ConsumedEnergy(String id, double value, double delta, Timestamp timestamp, boolean isPrediction) {
        super(id, value, timestamp, isPrediction);
        this.delta = delta;
    }

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }

    @Override
    public String toString(){
        return String.format("[%d, %f, %f, %s, %b]", super.getId(), super.getValue(), getDelta(), super.getTimestamp().toString(), super.isPrediction());
    }
}
