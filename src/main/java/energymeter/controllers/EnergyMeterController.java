package energymeter.controllers;

import energymeter.dao.EnergyMeterDAO;
import energymeter.model.EnergyAbstract;
import energymeter.util.EnergyTypesEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 7/10/15.
 */
@RestController
public class EnergyMeterController {

    ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Module.xml");

    EnergyMeterDAO energyMeterDAO = (EnergyMeterDAO) context.getBean("energyMeterDAO");

    @RequestMapping("energy/stat/{type}/{deviceID}/{date}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getByDeviceDate(@PathVariable(value = "type") String type,
                                              @PathVariable(value = "deviceID") String deviceID,
                                              @PathVariable(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
        try {
            ArrayList<EnergyAbstract> energies = energyMeterDAO.getEnergyDay(typeEnum, deviceID, date);
            return energies;
        }
        catch(Exception e) {
            return new ArrayList<EnergyAbstract>();
        }
    }

    @RequestMapping("energy/stat/{type}/{deviceID}/{dateFrom}/{dateTo}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getByDeviceDates(@PathVariable(value = "type") String type,
                                               @PathVariable(value = "deviceID") String deviceID,
                                               @PathVariable(value = "dateFrom") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateFrom,
                                               @PathVariable(value = "dateTo") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateTo) {
        EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
        try {
            ArrayList<EnergyAbstract> energies = energyMeterDAO.getEnergyPeriod(typeEnum, deviceID, dateFrom, dateTo);
            return energies;
        }
        catch(Exception e) {
            return new ArrayList<EnergyAbstract>();
        }
    }

    @RequestMapping("energy/stat/latest/{deviceID}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getByDeviceDates(@PathVariable(value = "deviceID") String deviceID) throws Exception {
        ArrayList<EnergyAbstract> energies = energyMeterDAO.getLatest(deviceID);
        return energies;
    }
}
