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
import java.util.Date;


@RestController
public class ConsumedEnergyController {

    ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Module.xml");

    ConsumedEnergyDAO consumedenergyDAO = (ConsumedEnergyDAO) context.getBean("consumedenergyDAO");

    @RequestMapping(value="/consumed", method=RequestMethod.GET)
    public String getAll(@RequestParam(value = "limit", required=false) Integer limit) {
        ArrayList<ConsumedEnergy> consumedEnergyPortions = consumedenergyDAO.getAllConsumedEnergy(limit);
        System.out.println(consumedEnergyPortions);
        return consumedEnergyPortions.toString();
    }

    @RequestMapping("consumed/{deviceID}")
    public @ResponseBody
    String getByDevice(@PathVariable(value="deviceID") int deviceID,
                       @RequestParam(value = "limit", required=false) Integer limit) {
        ArrayList<ConsumedEnergy> consumedEnergies = consumedenergyDAO.getConsumedEnergyById(deviceID, limit);
        return consumedEnergies.toString();
    }

    @RequestMapping("consumed/{deviceID}/{date}")
    public @ResponseBody
    String getByDeviceDate(@PathVariable(value="deviceID") int deviceID,
                           @PathVariable(value="date") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date date,
                           @RequestParam(value = "limit", required=false) Integer limit) {
        ArrayList<ConsumedEnergy> consumedEnergies = consumedenergyDAO.getConsumedEnergyByIdDate(deviceID, date, limit);
        return consumedEnergies.toString();
    }
}
