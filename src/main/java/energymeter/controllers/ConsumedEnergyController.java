package energymeter.controllers;

/**
 * Created by wojtek on 7/3/15.
 */
import energymeter.dao.ConsumedEnergyDAO;
import energymeter.model.ConsumedEnergy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;


@RestController
public class ConsumedEnergyController {
    ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Module.xml");

    @RequestMapping(value="/consumed", method=RequestMethod.GET)
    public String TestGet(@RequestParam(value="ID", required=false) Integer ID) {
        ConsumedEnergyDAO consumedenergyDAO = (ConsumedEnergyDAO) context.getBean("consumedenergyDAO");
//        ConsumedEnergy consumedEnergy = consumedenergyDAO.getConsumedEnergyById(1111115022);
//       return consumedEnergy.toString();
        ArrayList<ConsumedEnergy> consumedEnergyPortions= consumedenergyDAO.getAllConsumedEnergy();
        System.out.println(consumedEnergyPortions);
        return consumedEnergyPortions.toString();
    }

    @RequestMapping("consumed/{deviceID}")
    public @ResponseBody
    String getByDevice(@PathVariable(value="deviceID") String deviceID,
                                     String someAttr) {
        return deviceID;
    }

    @RequestMapping("consumed/{deviceID}/{date}")
    public @ResponseBody
    String getByDeviceDate(@PathVariable(value="deviceID") String deviceID,
                       @PathVariable(value="date") String date) {
        return deviceID;
    }
}
