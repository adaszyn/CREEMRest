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

    @RequestMapping(value="energydata/{type}", method=RequestMethod.GET)
    public ArrayList<EnergyAbstract> getAll(@PathVariable(value="type") String type,
                         @RequestParam(value = "limit", required=false) Integer limit) throws Exception {
        EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
        ArrayList<EnergyAbstract> energyAbstractPortions = energyDAO.getAllEnergy(typeEnum, limit);
        return energyAbstractPortions;
    }

    @RequestMapping("{type}/{deviceID}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getByDevice(@PathVariable(value="type") String type,
                       @PathVariable(value="deviceID") int deviceID,
                       @RequestParam(value = "limit", required=false) Integer limit) throws Exception {
        EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
        ArrayList<EnergyAbstract> energies = energyDAO.getEnergyById(typeEnum, deviceID, limit);
        return energies;
    }

    @RequestMapping("energydata/{type}/{deviceID}/{date}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getByDeviceDate(@PathVariable(value="type") String type,
                           @PathVariable(value="deviceID") int deviceID,
                           @PathVariable(value="date") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date date,
                           @RequestParam(value = "limit", required=false) Integer limit) throws Exception {
        EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
        ArrayList<EnergyAbstract> energies = energyDAO.getEnergyByIdDate(typeEnum, deviceID, date, limit);
        return energies;
    }

    @RequestMapping("energydata/{type}/{deviceID}/{datefrom}/{dateto}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getByDeviceDates(@PathVariable(value="type") String type,
                                              @PathVariable(value="deviceID") int deviceID,
                                              @PathVariable(value="datefrom") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date datefrom,
                                               @PathVariable(value="dateto") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) Date dateto,
                                              @RequestParam(value = "limit", required=false) Integer limit) throws Exception {
        EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
        ArrayList<EnergyAbstract> energies = energyDAO.getEnergyByIdDates(typeEnum, deviceID, datefrom, dateto, limit);
        return energies;
    }

    @RequestMapping("meters")
    public @ResponseBody
    ArrayList<String> getByDeviceDates() {
        ArrayList<String> meters = energyDAO.getMeters();
        return meters;
    }
}
