package com.reeflog.reeflogapi.beans;

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
    private TypeEvent type;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Aquarium aquarium;

    private Date date;
    private String description;

    public enum TypeEvent { EQUIPMENT, MAINTENANCE, ANIMAL, OTHER }



}
