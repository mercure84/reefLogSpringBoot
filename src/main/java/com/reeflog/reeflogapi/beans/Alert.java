package com.reeflog.reeflogapi.beans;

import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Alert {

    @Id
    @GeneratedValue
    private int id;
    private WaterTest.TypeTest typeTest;
    private float targetValue;
    private int dayInterval = 7;
    private boolean isActive = false;
    @ManyToOne
    private Aquarium aquarium;


}
