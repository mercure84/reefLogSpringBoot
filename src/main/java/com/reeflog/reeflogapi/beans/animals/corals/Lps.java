package com.reeflog.reeflogapi.beans.animals.corals;


import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Lps extends Coral {

    private LpsSpecies lpsSpecies;

    public enum LpsSpecies {EUPHYLLIA, CATALAPHYLLIA, ACANTHASTREA, SCOLYMIA, LOBOPHYLLIA, SYMPHYLLIA, FAVIA, ECHINOPHYLLIA, ECHINOPORA, PECTINIA, TRACHYPHYLLIA, FUNGIA, HELIOFUNGIA, DUNCANOPSAMMIA, TURBBINARIA, CAULASTREA, PLEROGYRA, GONIOPORA}


}
