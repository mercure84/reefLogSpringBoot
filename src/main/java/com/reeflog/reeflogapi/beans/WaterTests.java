package com.reeflog.reeflogapi.beans;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class WaterTests {
    @Id
    @GeneratedValue
    private int id;
    private Date date;

    @ManyToOne
    private Member member;

}
