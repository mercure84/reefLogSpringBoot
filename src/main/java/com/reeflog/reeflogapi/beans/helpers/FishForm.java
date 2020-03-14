package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.animals.fishes.Fish;
import lombok.Data;

@Data
public class FishForm {

    private Fish fish;
    private int aquariumId;

}
