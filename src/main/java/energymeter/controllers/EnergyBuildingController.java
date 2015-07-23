package energymeter.controllers;

import energymeter.dao.EnergyBuildingDAO;
import energymeter.dao.EnergyMeterDAO;
import energymeter.model.BuildingEnergy;
import energymeter.model.EnergyAbstract;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * Created by root on 7/22/15.
 */

@RestController
public class EnergyBuildingController {
    ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Module.xml");

    EnergyBuildingDAO energyMeterDAO = (EnergyBuildingDAO) context.getBean("energyBuildingDAO");

    @RequestMapping("energy/building")
    public @ResponseBody
    ArrayList<BuildingEnergy> getByDeviceDate() {
        try {
            ArrayList<BuildingEnergy> energies = energyMeterDAO.getBuildingEnergy();
            return energies;
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }
}
