package com.reeflog.reeflogapi.beans.aquariums;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reeflog.reeflogapi.beans.Member;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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

    @JsonIgnore
    private byte[] picture ;

    private Date startDate;

    @ManyToOne
    private Member member;

    private int sumpVolume;

    public float getRawVolume() {
        return (this.height * this.length * this.width / 1000);
    }


}
