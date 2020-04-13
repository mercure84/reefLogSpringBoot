package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.Alert;
import lombok.Data;

import java.util.List;

@Data
public class AlertForm {
    private int aquariumId;
    private List<Alert> alerts ;

}
