package com.reeflog.reeflogapi.beans.animals.corals;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Soft extends Coral {

    public SoftSpecies softSpecies ;

    public enum SoftSpecies { ZOANTHUS, DISCOSOMA, RICORDEA, RHODACHTHIS, SARCOPHYTON, SINULARIA, XENIA, CORNULARIUA, ANTHELLIA }

}
