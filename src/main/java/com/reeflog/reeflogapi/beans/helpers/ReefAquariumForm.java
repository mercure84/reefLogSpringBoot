package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.ReefAquarium;
import lombok.Data;

import java.util.Date;

@Data
public class ReefAquariumForm {

    private int memberId;
    private String name;
    private ReefAquarium.TypeOfMaintenance typeOfMaintenance;
    private ReefAquarium.MainPopulation mainPopulation;
    private float length;
    private float width;
    private float height;
    private int sumpVolume;
    private Date startDate;
    private int liveRocksWeigth;
    private int othersRocksWeight;

}
