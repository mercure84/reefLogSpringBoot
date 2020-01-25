package com.reeflog.reeflogapi.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public abstract class Aquarium {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    // dimensions in cm
    private float length;
    private float width;
    private float height;

    private Date startDate;

    @OneToMany
    private List<Equipment> equipmentList;

    @ManyToOne
    private Member member;

    private int sumpVolume;

    public float getRawVolume() {
        return (this.height * this.length * this.width / 1000);
    }


}
