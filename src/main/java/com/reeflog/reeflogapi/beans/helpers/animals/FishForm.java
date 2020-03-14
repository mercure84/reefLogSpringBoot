package com.reeflog.reeflogapi.beans.helpers.animals;

import com.reeflog.reeflogapi.beans.animals.fishes.Fish;
import lombok.Data;

@Data
public class FishForm {

    private Fish fish;
    private int aquariumId;

}
