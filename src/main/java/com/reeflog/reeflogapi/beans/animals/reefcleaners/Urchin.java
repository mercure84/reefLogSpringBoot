package com.reeflog.reeflogapi.beans.animals.reefcleaners;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Urchin extends ReefCleaner{

    private UrchinSpecies urchinSpecies ;

    public enum UrchinSpecies {TRIPNEUSTES, PHYLLACANTHUS, SALMACIS, MESPILIA, LAGANUM, ASTROPYGA, DIADEMA, CLYPEASTER, HETEROCENTROTUS}

}
