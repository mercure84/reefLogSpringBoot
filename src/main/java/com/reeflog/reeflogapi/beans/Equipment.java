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
    private String type;
    private String mark;
    private String description;


    @ManyToOne
    private Aquarium aquarium;

    }
