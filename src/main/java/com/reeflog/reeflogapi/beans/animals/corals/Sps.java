package com.reeflog.reeflogapi.beans.animals.corals;


import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Sps extends Coral{

    private SpsSpecies spsSpecies ;
    public enum SpsSpecies {ACCROPORA, MONTIPORA, POCCILLOPORA, SERIATOPORA, STYLOPHORA}

}
