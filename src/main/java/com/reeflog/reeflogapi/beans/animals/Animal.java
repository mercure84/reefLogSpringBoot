package com.reeflog.reeflogapi.beans.animals;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
public abstract class Animal {

    @Id
    @GeneratedValue
    private int id;
    private String name ;
    private Date incomingDate = new Date();
    private Date exitDate;
    private Date birthDate;
    private Date deathDate ;
    private String notes = null ;
    private Size size = Size.M ;
    private String origin = null;
    private Sex sex = Sex.UNDEFINED;
    private enum Size {XS, S, M, L, XL}
    private enum Sex {MALE, FEMALE, UNDEFINED}

    private int quantity = 1;

    private Date lastPresenceDate = null;

    @ManyToOne
    @JsonIgnore
    private Aquarium aquarium;

}
