package com.reeflog.reeflogapi.beans.animals.reefcleaners;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Cucumber extends ReefCleaner{

    private CucumberSpecies cucumberSpecies;

    public enum CucumberSpecies {HOLOTHURIA, PSEUDOCOLOCHIRUS, ACTINOPYGA, SYNAPTULA}

}
