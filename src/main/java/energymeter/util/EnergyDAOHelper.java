package energymeter.util;

import com.fasterxml.jackson.core.sym.NameN;
import energymeter.dao.EnergyDAO;
import energymeter.model.ConsumedEnergy;
import energymeter.model.EnergyAbstract;
import energymeter.model.ProducedEnergy;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by root on 7/10/15.
 */

public class EnergyDAOHelper {

    public static Double sumArrayList(ArrayList<Double> list) {
        Double sum = 0.0;
        for (Double el: list) {
            sum += el;
        }
        return sum;
    }

    public static Double getAverage(ArrayList<Double> list) {
        Double sum = sumArrayList(list);
        return sum / list.size();
    }

    public static ArrayList<EnergyAbstract> getData(PreparedStatement ps, EnergyTypesEnum type) throws Exception {
        ResultSet rs = ps.executeQuery();
        EnergyAbstract objectType;
        ArrayList<EnergyAbstract> energyResults = new ArrayList<>();
        while (rs.next()) {
            objectType = EnergyFactory.getEnergyInstance(type);
            objectType.setId(rs.getString("DEVICE_ID"));
            objectType.setValue(rs.getDouble("MEASURE_VALUE"));
            objectType.setTimestamp(rs.getTimestamp("MEASURE_TIMESTAMP"));
            objectType.setIsPrediction(false);
            objectType.setType(type.getTable());
            energyResults.add(objectType);
        }
        rs.close();
        ps.close();
        return energyResults;
    }

    public static ArrayList<EnergyAbstract> getLatestData(PreparedStatement ps) throws Exception {
        ResultSet rs = ps.executeQuery();
        EnergyAbstract objectType;
        ArrayList<EnergyAbstract> energyResults = new ArrayList<>();
        while (rs.next()) {
            objectType = EnergyFactory.getEnergyInstance("t_data_" + rs.getString("MEASURE_TYPE"));
            objectType.setId(rs.getString("DEVICE_ID"));
            objectType.setValue(rs.getDouble("MEASURE_VALUE"));
            objectType.setTimestamp(rs.getTimestamp("MEASURE_TIMESTAMP"));
            objectType.setIsPrediction(false);
            objectType.setType(rs.getString("MEASURE_TYPE"));
            energyResults.add(objectType);
        }
        rs.close();
        ps.close();
        return energyResults;
    }

    public static double getValuePerTime(ArrayList<EnergyAbstract> energyResults, long time) {
        double valueDif = energyResults.get(energyResults.size() - 1).getValue() - energyResults.get(0).getValue();
        long timeDif = energyResults.get(energyResults.size() - 1).getTimestamp().getTime() - energyResults.get(0).getTimestamp().getTime();
        double perHour = time / (double) timeDif;
        double valuePerTime = valueDif * perHour;
        return valuePerTime;
    }

    public static ArrayList<EnergyAbstract> getFinalResults(Timestamp timeThen, EnergyTypesEnum type,
                                                            ArrayList<ArrayList<Double>> timePeriods, String id,
                                                            double ValuePerTime, long time, long timeDiff) throws Exception {
        EnergyAbstract objectType;
        ArrayList<EnergyAbstract> energyTime = new ArrayList<>();
        for (int i=0; i<timeDiff; i++) {
            objectType = EnergyFactory.getEnergyInstance(type);
            objectType.setId(id);
            objectType.setTimestamp(timeThen);
            if (timePeriods.get(i).size() > 0) {
                objectType.setIsPrediction(false);
                objectType.setValue(EnergyDAOHelper.getAverage(timePeriods.get(i)));
            }
            else {
                objectType.setIsPrediction(true);
                try {
                    if (energyTime.get(i-1).getValue() != -1.0) {
                        objectType.setValue(energyTime.get(i - 1).getValue() + ValuePerTime);
                    }
                    else {
                        objectType.setValue(-1.0);
                    }
                }
                catch(ArrayIndexOutOfBoundsException e) {
                    objectType.setValue(-1.0);
                }
            }
            if (type.toString().contains("CONSUMED")) {
                ((ConsumedEnergy) objectType).setDelta(ValuePerTime);
            }
            else if (type.toString().contains("PRODUCED")) {
                ((ProducedEnergy) objectType).setDelta(ValuePerTime);
            }
            energyTime.add(objectType);
            timeThen = new Timestamp(timeThen.getTime() + time);
        }
        for (int i=(int)timeDiff-1; i>=0; i--) {
            if (i < timeDiff-1 && energyTime.get(i).getValue() > energyTime.get(i+1).getValue()) {
                energyTime.get(i).setValue(energyTime.get(i + 1).getValue());
            }
            if (energyTime.get(i).getValue() == -1.0) {
                energyTime.remove(i);
            }
        }
        return energyTime;
    }


    public static ArrayList<EnergyAbstract> getPowerResults(Timestamp timeThen, ArrayList<ArrayList<Double>> timePeriods,
                                                            String id, long time, long timeDiff, ArrayList<EnergyAbstract> lastEnergyPeriod) throws Exception {
        EnergyAbstract objectType;
        ArrayList<EnergyAbstract> energyTime = new ArrayList<>();
        for (int i=0; i<timeDiff; i++) {
            objectType = EnergyFactory.getEnergyInstance(EnergyTypesEnum.TOTAL_ACTIVE_POWER);
            objectType.setId(id);
            objectType.setTimestamp(timeThen);
            if (timePeriods.get(i).size() > 0) {
                objectType.setIsPrediction(false);
                objectType.setValue(EnergyDAOHelper.getAverage(timePeriods.get(i)));
                energyTime.add(objectType);
            }
            else {
                objectType.setIsPrediction(true);
                if (lastEnergyPeriod.get(i).isPrediction() == false) {
                    objectType.setValue(lastEnergyPeriod.get(i).getValue());
                    energyTime.add(objectType);
                }
            }
            timeThen = new Timestamp(timeThen.getTime() + time);
        }
        return energyTime;
    }

    public static void arrangeData(ArrayList<EnergyAbstract> results, java.util.Date dateFrom,
                                                 TimeUnit unit, ArrayList<ArrayList<Double>> periods) {
        for (EnergyAbstract en:results) {
            long tmpDate = en.getTimestamp().getTime() - dateFrom.getTime();
            tmpDate = unit.convert(tmpDate, TimeUnit.MILLISECONDS);
            periods.get((int)tmpDate).add(en.getValue());
        }
    }
}
