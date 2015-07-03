package energy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public class EnergyController {
    @RequestMapping(value="/energy1", method= RequestMethod.GET)
    public !!!!!!!!!!!! EnergyGet1() {
        return getEnergy1();
    }

    @RequestMapping(value="/energy2", method= RequestMethod.GET)
    public !!!!!!!!!!!! EnergyGet2() {
        return getEnergy2();
    }
}
