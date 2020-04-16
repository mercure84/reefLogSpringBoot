package com.reeflog.reeflogapi.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Event {

    @Id
    @GeneratedValue
    private int id;
    private TypeEvent type = TypeEvent.OTHER;

    @ManyToOne
    @JsonIgnore
    private Aquarium aquarium;

    private Date date = new Date();
    private String title;
    private String description;

    public enum TypeEvent { TREATEMENT, MAINTENANCE, ANIMAL, OTHER }

}
