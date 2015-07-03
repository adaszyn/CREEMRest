package energymeter.controllers;

/**
 * Created by wojtek on 7/3/15.
 */
import energymeter.dao.ConsumedEnergyDAO;
import energymeter.model.ConsumedEnergy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.sql.Date;


@RestController
public class ConsumedEnergyController {

    ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Module.xml");

    ConsumedEnergyDAO consumedenergyDAO = (ConsumedEnergyDAO) context.getBean("consumedenergyDAO");

    @RequestMapping(value="/consumed", method=RequestMethod.GET)
    public String TestGet(@RequestParam(value="ID", required=false) Integer ID) {
        ArrayList<ConsumedEnergy> consumedEnergyPortions= consumedenergyDAO.getAllConsumedEnergy();
        System.out.println(consumedEnergyPortions);
        return consumedEnergyPortions.toString();
    }

    @RequestMapping("consumed/{deviceID}")
    public @ResponseBody
    String getByDevice(@PathVariable(value="deviceID") int deviceID) {
        ArrayList<ConsumedEnergy> consumedEnergies = consumedenergyDAO.getConsumedEnergyById(deviceID);
        return consumedEnergies.toString();
    }

    @RequestMapping("consumed/{deviceID}/{date}")
    public @ResponseBody
    String getByDeviceDate(@PathVariable(value="deviceID") int deviceID,
                           @PathVariable(value="date") @DateTimeFormat(pattern="yyyy-mm-dd") String date) {
//        NIE DZIAla
        ArrayList<ConsumedEnergy> consumedEnergies = consumedenergyDAO.getConsumedEnergyByIdDate(deviceID, date);
        return consumedEnergies.toString();
    }
}
