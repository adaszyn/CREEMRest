package energy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

public class EnergyController {
    @RequestMapping(value="/energy", method= RequestMethod.GET)
    public ArrayList<EnergyData> EnergyGet() {
        return new EnergyDaoImpl().getEnergy();
    }
}
