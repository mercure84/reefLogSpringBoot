package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.animals.corals.Anemone;
import com.reeflog.reeflogapi.beans.animals.corals.Lps;
import com.reeflog.reeflogapi.beans.animals.corals.Soft;
import com.reeflog.reeflogapi.beans.animals.corals.Sps;
import com.reeflog.reeflogapi.beans.animals.fishes.Fish;
import com.reeflog.reeflogapi.beans.animals.reefcleaners.*;
import lombok.Data;

@Data
public class AnimalForm {

    private int aquariumId;
    private Anemone anemone = null;
    private Lps lps = null;
    private Sps sps = null;
    private Soft soft = null;
    private Fish fish = null;
    private Crustacean crustacean = null;
    private Cucumber cucumber = null;
    private Mollusk mollusk = null;
    private Star star = null;
    private Urchin urchin = null;

}
