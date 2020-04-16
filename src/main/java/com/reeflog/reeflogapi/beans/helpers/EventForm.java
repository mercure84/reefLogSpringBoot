package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.Event;
import lombok.Data;

@Data
public class EventForm {
    private Event event;
    private int aquariumId;

}
