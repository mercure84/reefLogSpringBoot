package com.reeflog.reeflogapi.beans;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class ReefAquarium extends Aquarium {

    private enum TypeOfMaintenance {BERLINOIS, JAUBERT, AUTRE}
    private enum MainPopulation {FISH_ONLY, SOFT, LPS, SPS, MIX }
    private String ballingDescription;

}
