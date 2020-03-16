package com.reeflog.reeflogapi.beans.animals.reefcleaners;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Crustacean extends ReefCleaner{

    private CrustaceanSpecies crustaceanSpecies ;

    public enum CrustaceanSpecies {
        CALCINUS, CLIBANARIUS, DARDANUS, PAGURISTES, TRIZOPAGURUS, TRAPEZIA, CALAPPA, CAMPOSCIA, ILIA, IXA, LEUCOSIA, lISSOCARCINUS, MITHRAX,
        ALPHEUS, GONODACTYLUS, HYMENOCERA, LATREILLIA, LYSMATA, ODONTODACTYLUS, STENOPUS, THOR

    }
}
