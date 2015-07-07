package energymeter.model;
import java.sql.Timestamp;

/**
 * Created by root on 7/6/15.
 */

public class ProducedEnergy extends EnergyAbstract {
    private double delta;

    public ProducedEnergy() {}

    public ProducedEnergy(int id, double value, double delta, Timestamp timestamp) {
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
        return String.format("[%d, %f, %f]", id, value, delta);
    }
}