package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.animals.Fish;
import lombok.Data;

@Data
public class AnimalForm {

    private int aquariumId;
    private Fish fish = null;

}
