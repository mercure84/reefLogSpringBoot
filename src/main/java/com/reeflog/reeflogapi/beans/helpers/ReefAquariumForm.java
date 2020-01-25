package com.reeflog.reeflogapi.beans.helpers;

import lombok.Data;

import java.util.Date;

@Data
public class ReefAquariumForm {

    private int memberId;
    private String name;
    private float length;
    private float width;
    private float height;
    private int sumpVolume;
    private Date startDate;

}
