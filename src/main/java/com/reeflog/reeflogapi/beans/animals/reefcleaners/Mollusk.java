package com.reeflog.reeflogapi.beans.animals.reefcleaners;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Mollusk extends ReefCleaner{

    private MolluskSpecies molluskSpecies ;

    public enum MolluskSpecies {ASTRALIUM, ASTREA, BABYLONIA, CERITHIUM, CYPRAEA, LAMBIS, NASSARIUS, NERITA, STROMBUS, TECTUS, TROCHUS, TURBO}



}
