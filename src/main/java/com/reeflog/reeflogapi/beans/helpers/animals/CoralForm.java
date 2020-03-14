package com.reeflog.reeflogapi.beans.helpers.animals;

import com.reeflog.reeflogapi.beans.animals.corals.Anemone;
import com.reeflog.reeflogapi.beans.animals.corals.Lps;
import com.reeflog.reeflogapi.beans.animals.corals.Soft;
import com.reeflog.reeflogapi.beans.animals.corals.Sps;
import lombok.Data;

@Data
public class CoralForm {

    private int aquariumId;
    private Anemone anemone = null;
    private Lps lps = null;
    private Sps sps = null;
    private Soft soft = null;
    }
