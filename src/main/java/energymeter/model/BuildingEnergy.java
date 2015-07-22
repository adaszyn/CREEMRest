package energymeter.model;

/**
 * Created by root on 7/22/15.
 */
public class BuildingEnergy {
    private String pod;
    private Double potenza_picco = null, energia_totale_anno = null, densita_energia_anno = null;

    public BuildingEnergy() {}

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public Double getPotenza_picco() {
        return potenza_picco;
    }

    public void setPotenza_picco(Double potenza_picco) {
        this.potenza_picco = potenza_picco;
    }

    public Double getEnergia_totale_anno() {
        return energia_totale_anno;
    }

    public void setEnergia_totale_anno(Double energia_totale_anno) {
        this.energia_totale_anno = energia_totale_anno;
    }

    public Double getDensita_energia_anno() {
        return densita_energia_anno;
    }

    public void setDensita_energia_anno(Double densita_energia_anno) {
        this.densita_energia_anno = densita_energia_anno;
    }
}
