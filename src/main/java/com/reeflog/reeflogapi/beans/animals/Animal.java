package com.reeflog.reeflogapi.beans.animals;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public abstract class Animal {

    @Id
    @GeneratedValue
    private int id;
    private int quantity;
    private String name ;
    private Date incomingDate = new Date();
    private Date exitDate = null ;
    private Date birthDay = null;
    private Date deathDay = null;
    private String notes ;
    private CurrentSize currentSize ;
    public enum CurrentSize {XS, S, M, L, XL}

}
