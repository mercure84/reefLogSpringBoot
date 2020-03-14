package com.reeflog.reeflogapi.beans.aquariums;

import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class ReefAquarium extends Aquarium {

    private TypeOfMaintenance typeOfMaintenance;
    private MainPopulation mainPopulation;
    private String ballingDescription;
    private int liveRocksWeigth;
    private int othersRocksWeight;

    public enum TypeOfMaintenance {BERLINOIS, JAUBERT, AUTRE}
    public enum MainPopulation {FISH_ONLY, SOFT, LPS, SPS, MIX }


}
