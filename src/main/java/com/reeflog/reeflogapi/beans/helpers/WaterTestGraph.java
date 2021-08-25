package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.WaterTest;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WaterTestGraph {

    WaterTest.TypeTest typeTest;
    List<Measure> measures = new ArrayList<Measure>();


}
