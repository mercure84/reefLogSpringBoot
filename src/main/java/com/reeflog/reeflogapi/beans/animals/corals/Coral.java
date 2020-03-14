package com.reeflog.reeflogapi.beans.animals.corals;


import com.reeflog.reeflogapi.beans.animals.Animal;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Coral extends Animal {

    public enum CoralType {ANEMONE, LPS, SPS, SOFT}

}
