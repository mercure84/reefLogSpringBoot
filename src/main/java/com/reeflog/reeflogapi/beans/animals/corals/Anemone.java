package com.reeflog.reeflogapi.beans.animals.corals;


import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Anemone extends Coral {

    private AnemoneSpecies anemoneSpecies;

    public enum AnemoneSpecies {ENTACMEA, HETERACTIS, STICHODACTYLA, CERIANTHUS, MACRODACTYLA}


}
