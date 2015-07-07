package energymeter.controllers;

/**
 * Created by wojtek on 7/3/15.
 */
import energymeter.dao.EnergyDAO;
import energymeter.model.EnergyAbstract;
import energymeter.util.EnergyTypesEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;


@RestController
public class EnergyController {

    ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Module.xml");

    EnergyDAO energyDAO = (EnergyDAO) context.getBean("consumedenergyDAO");

    @RequestMapping(value="/{type}", method=RequestMethod.GET)
    public String getAll(@PathVariable(value="type") String type,
                         @RequestParam(value = "limit", required=false) Integer limit) throws Exception {
        EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
        ArrayList<EnergyAbstract> energyAbstractPortions = energyDAO.getAllEnergy(typeEnum, limit);
        return energyAbstractPortions.toString();
    }

    @RequestMapping("{type}/{deviceID}")
    public @ResponseBody
    String getByDevice(@PathVariable(value="type") String type,
                       @PathVariable(value="deviceID") int deviceID,
                       @RequestParam(value = "limit", required=false) Integer limit) throws Exception {
        EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
        ArrayList<EnergyAbstract> energies = energyDAO.getEnergyById(typeEnum, deviceID, limit);
        return energies.toString();
    }

    @RequestMapping("{type}/{deviceID}/{date}")
    public @ResponseBody
    String getByDeviceDate(@PathVariable(value="type") String type,
                           @PathVariable(value="deviceID") int deviceID,
                           @PathVariable(value="date") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date date,
                           @RequestParam(value = "limit", required=false) Integer limit) throws Exception {
        EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
        ArrayList<EnergyAbstract> energies = energyDAO.getEnergyByIdDate(typeEnum, deviceID, date, limit);
        return energies.toString();
    }
}
