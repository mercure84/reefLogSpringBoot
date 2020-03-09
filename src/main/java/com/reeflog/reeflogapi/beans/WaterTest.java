package com.reeflog.reeflogapi.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class WaterTest {
    @Id
    @GeneratedValue
    private int id;
    private Date date;
    private float temperature;
    private float salinity;
    private float alcalinity;
    private float ph;
    private int calcium;
    private int magnesium;
    private float ammoniac;
    private float nitrates;
    private float nitrites;
    private float phosphates;
    private float silicates;

    public enum TypeTest {TEMPERATURE, SALINITY, ALCALINITY, PH, CALCIUM, MAGNESIUM, AMMONIAC, NITRATES, NITRITES, PHOSPHATES, SILICATES}

    @ManyToOne
    private Aquarium aquarium;

}
