package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.WaterTest;
import lombok.Data;

@Data
public class WaterTestForm {

    private WaterTest waterTest;
    private int aquariumId;

}
