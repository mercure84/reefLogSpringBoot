package com.reeflog.reeflogapi.beans;

import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
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
    private Float temperature;
    private Float salinity;
    private Float alcalinity;
    private Float ph;
    private Integer calcium;
    private Integer magnesium;
    private Float ammoniac;
    private Float nitrates;
    private Float nitrites;
    private Float phosphates;
    private Float silicates;

    public enum TypeTest {TEMPERATURE, SALINITY, ALCALINITY, PH, CALCIUM, MAGNESIUM, AMMONIAC, NITRATES, NITRITES, PHOSPHATES, SILICATES}


    @ManyToOne
    private Aquarium aquarium;

}
