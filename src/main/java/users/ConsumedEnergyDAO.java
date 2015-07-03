package users;
/**
 * Created by wojtek on 7/3/15.
 */
public interface ConsumedEnergyDAO {
    public void insert(ConsumedEnergy consumedEnergy);
    public ConsumedEnergy getConsumedEnergyById(int id);
}
