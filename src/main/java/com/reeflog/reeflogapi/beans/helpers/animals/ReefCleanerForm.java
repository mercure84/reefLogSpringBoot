package com.reeflog.reeflogapi.beans.helpers.animals;

import com.reeflog.reeflogapi.beans.animals.reefcleaners.*;
import lombok.Data;

@Data
public class ReefCleanerForm {

    private int aquariumId;
    private Crustacean crustacean = null;
    private Cucumber cucumber = null;
    private Mollusk mollusk = null;
    private Star star = null;
    private Urchin urchin = null;

}
