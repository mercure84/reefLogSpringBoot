package com.reeflog.reeflogapi.beans.animals;

import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
public class Animal {

    @Id
    @GeneratedValue
    private int id;
    private int quantity = 1;
    private String name ;
    private Date incomingDate = new Date();
    private Date exitDate = null ;
    private Date birthDay = null;
    private Date deathDay = null;
    private String notes ;
    private CurrentSize currentSize = CurrentSize.M ;
    public enum CurrentSize {XS, S, M, L, XL}

    @ManyToOne
    private Aquarium aquarium;

}
