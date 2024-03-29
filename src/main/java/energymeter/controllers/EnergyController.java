package energymeter.controllers;

/**
 * Created by wojtek on 7/3/15.
 */
import energymeter.dao.EnergyDAO;
import energymeter.dao.PredictionDAO;
import energymeter.model.EnergyAbstract;
import energymeter.util.EnergyTypesEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


@RestController
public class EnergyController {

    ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Module.xml");

    EnergyDAO energyDAO = (EnergyDAO) context.getBean("consumedenergyDAO");
    PredictionDAO predictionDAO = (PredictionDAO) context.getBean("predictionDAO");

    @RequestMapping(value="energy/data/{type}", method=RequestMethod.GET)
    public ArrayList<EnergyAbstract> getAll(@PathVariable(value="type") String type,
                         @RequestParam(value = "limit", required=false) Integer limit) throws Exception {
        try {
            EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
            ArrayList<EnergyAbstract> energyAbstractPortions = energyDAO.getAllEnergy(typeEnum, limit);
            return energyAbstractPortions;
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }

    @RequestMapping("energy/data/{type}/{deviceID}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getByDevice(@PathVariable(value="type") String type,
                       @PathVariable(value="deviceID") String deviceID,
                       @RequestParam(value = "limit", required=false) Integer limit) throws Exception {
        try {
            EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
            ArrayList<EnergyAbstract> energies = energyDAO.getEnergyById(typeEnum, deviceID, limit);
            return energies;
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }

    @RequestMapping("energy/data/{type}/{deviceID}/{date}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getByDeviceDate(@PathVariable(value="type") String type,
                           @PathVariable(value="deviceID") String deviceID,
                           @PathVariable(value="date") String dateStr,
                           @RequestParam(value = "limit", required=false) Integer limit) throws Exception {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date date = format.parse(dateStr);
            EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
            ArrayList<EnergyAbstract> energies = energyDAO.getEnergyByIdDate(typeEnum, deviceID, date, limit);
            return energies;
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }

    @RequestMapping("energy/data/{type}/{deviceID}/{dateFrom}/{dateTo}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getByDeviceDates(@PathVariable(value="type") String type,
                                              @PathVariable(value="deviceID") String deviceID,
                                              @PathVariable(value="dateFrom") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) String dateFromStr,
                                               @PathVariable(value="dateTo") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) String dateToStr,
                                              @RequestParam(value = "limit", required=false) Integer limit) throws Exception {
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Date dateFrom = format.parse(dateFromStr);
            Date dateTo = format.parse(dateToStr);
            EnergyTypesEnum typeEnum = EnergyTypesEnum.valueOf(type.toUpperCase());
            ArrayList<EnergyAbstract> energies = energyDAO.getEnergyByIdDates(typeEnum, deviceID, dateFrom, dateTo, limit);
            return energies;
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }

    @RequestMapping("meters")
    public @ResponseBody
    ArrayList<EnergyAbstract> getByDeviceDates() throws Exception {
        ArrayList<EnergyAbstract> meters = energyDAO.getMeters();
        return meters;
    }

    @RequestMapping("datasets")
    public @ResponseBody
    EnergyTypesEnum [] getDatasets(){
        EnergyTypesEnum [] datasets = EnergyTypesEnum.values();
        return datasets;
    }

    @RequestMapping("predict/{deviceID}")
    public @ResponseBody
    ArrayList<EnergyAbstract> getPredictions(@PathVariable(value="deviceID") String deviceID,
                                             @RequestParam(value="days", required=false) Integer days,
                                             @RequestParam(value="limit", required=false) Integer limit) throws Exception {
        try {
            ArrayList<EnergyAbstract> predictions = predictionDAO.predict(deviceID, days, limit);
            return predictions;
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }
}
