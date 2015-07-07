package energymeter.model;
import java.sql.Timestamp;

/**
 * Created by root on 7/6/15.
 */

public class ConsumedEnergy extends EnergyAbstract {

    private double delta;

    public ConsumedEnergy() {}

    public ConsumedEnergy(int id, double value, double delta, Timestamp timestamp) {
        super(id, value, timestamp);
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
        return String.format("[%d, %f, %f]", super.id, super.value, delta);
    }
}
