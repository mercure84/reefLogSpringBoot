package com.reeflog.reeflogapi.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class WaterTest {
    @Id
    @GeneratedValue
    private int id;
    private Date date;
    private TypeTest type;
    private float value;
    private String unit;

    public enum TypeTest {PHOSPHATES, NITRATED, CALCIUM, KH, PH}

    @ManyToOne
    private Member member;

    @ManyToOne
    private Aquarium aquarium;

}
