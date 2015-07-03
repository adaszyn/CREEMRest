package energy;

import java.util.Date;

public class EnergyData {
    private String date;
    private int energy_measure, deviceID;

    public EnergyData(String date, int energy_measure, int deviceID) {
        this.date = date;
        this.energy_measure = energy_measure;
        this.deviceID = deviceID;
    }

    public EnergyData() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEnergy_measure() {
        return energy_measure;
    }

    public void setEnergy_measure(int energy_measure) {
        this.energy_measure = energy_measure;
    }

    public int getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(int deviceID) {
        this.deviceID = deviceID;
    }
}
