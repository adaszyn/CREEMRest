package energymeter.controllers;

import energymeter.dao.EnergyMeterDAO;
import energymeter.model.EnergyAbstract;
import energymeter.util.EnergyTypesEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
                                              @PathVariable(value = "date") String dateStr) throws Exception {
        try {
            EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = format.parse(dateStr);
            ArrayList<EnergyAbstract> energies = energyMeterDAO.getEnergyDay(typeEnum, deviceID, date);
            return energies;
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }

    @RequestMapping("energy/stat/{type}/{deviceID}/{dateFrom}/{dateTo}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getByDeviceDates(@PathVariable(value = "type") String type,
                                               @PathVariable(value = "deviceID") String deviceID,
                                               @PathVariable(value = "dateFrom") String dateFromStr,
                                               @PathVariable(value = "dateTo") String dateToStr) throws Exception {
        try {
            EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date dateFrom = format.parse(dateFromStr);
            Date dateTo = format.parse(dateToStr);
            ArrayList<EnergyAbstract> energies = energyMeterDAO.getEnergyPeriod(typeEnum, deviceID, dateFrom, dateTo);
            return energies;
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }

    @RequestMapping("energy/stat/latest/{deviceID}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getByDeviceDates(@PathVariable(value = "deviceID") String deviceID) throws Exception {
        try {
            ArrayList<EnergyAbstract> energies = energyMeterDAO.getLatest(deviceID);
            return energies;
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }

    @RequestMapping("energy/latest/{date}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getLatestDate(@PathVariable(value = "date") String dateStr) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = format.parse(dateStr);
            ArrayList<EnergyAbstract> energies = energyMeterDAO.getLatestDate(date);
            return energies;
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }

    @RequestMapping("energy/energypower/{deviceID}/{step}/{dateFrom}/{dateTo}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getEnergyPower(@PathVariable(value = "step") String stepStr,
                                             @PathVariable(value = "deviceID") String deviceID,
                                             @PathVariable(value = "dateFrom") String dateFromStr,
                                             @PathVariable(value = "dateTo") String dateToStr) throws Exception {
        try {
            Long step = Long.parseLong(stepStr);
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date dateFrom = format.parse(dateFromStr);
            Date dateTo = format.parse(dateToStr);
            ArrayList<EnergyAbstract> energies = energyMeterDAO.getEnergyPower(step, deviceID, dateFrom, dateTo);
            return energies;
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }
}
