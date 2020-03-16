package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.Equipment;
import lombok.Data;

@Data
public class EquipmentForm {

    private int aquariumId;
    private Equipment equipment;

}
