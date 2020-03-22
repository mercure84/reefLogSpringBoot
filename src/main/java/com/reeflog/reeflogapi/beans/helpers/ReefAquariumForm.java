package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.aquariums.ReefAquarium;
import lombok.Data;


@Data
public class ReefAquariumForm {

    private int memberId;
    private ReefAquarium reefAquarium;

}
