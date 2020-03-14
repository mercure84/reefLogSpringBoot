package com.reeflog.reeflogapi.beans.helpers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.reeflog.reeflogapi.beans.aquariums.ReefAquarium;
import lombok.Data;

import java.util.Date;

//TODO refactoriser ce helperForm sur le modèle des autres helpers

@Data
public class ReefAquariumForm {

    private int memberId;
    private ReefAquarium reefAquarium;

}
