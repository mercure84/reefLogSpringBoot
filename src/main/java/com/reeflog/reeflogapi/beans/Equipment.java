package com.reeflog.reeflogapi.beans;

import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Equipment {

    @Id @GeneratedValue
    private int id;
    private TypeOfEquipment typeOfEquipment;
    private String mark;
    private String description;
    private float power;

    @ManyToOne
    private Aquarium aquarium;

    public enum TypeOfEquipment {SKIMMER, STREAMPUMP, RETURNPUMP, LIGHT, DOSINGPUMP, AIRPUMP, CONTROLLER, FILTER, HEATING, OSMOLATOR, ULTRA_V }

    }
