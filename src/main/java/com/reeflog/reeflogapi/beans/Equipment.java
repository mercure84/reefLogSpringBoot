package com.reeflog.reeflogapi.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Equipment {

    @Id @GeneratedValue
    private int id;
    private String type;
    private String mark;
    private String description;
    }
