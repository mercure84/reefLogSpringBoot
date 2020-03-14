package com.reeflog.reeflogapi.beans.animals.reefcleaners;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Star extends ReefCleaner {
    private StarSpecies starSpecies ;

    public enum StarSpecies {ACANTHASTER, ARCHASTER, FROMIA, CULCITA, LINCKIA, OPHIARACHNA, PENTACERASTER, MACROPHIOTHRIX, OPHIOMASTIX, OPHIOCOMA}


}
