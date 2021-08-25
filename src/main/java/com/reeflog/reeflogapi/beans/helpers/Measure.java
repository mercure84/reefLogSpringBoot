package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.WaterTest;
import lombok.Data;

import java.util.Date;

@Data
public class Measure {
    Date date;
    float value;

    public Measure(Date date, float value) {
        this.date = date;
        this.value = value;
    }
}