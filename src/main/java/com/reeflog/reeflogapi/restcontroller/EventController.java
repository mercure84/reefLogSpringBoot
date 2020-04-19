package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Event;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.helpers.EventForm;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.EventRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));


    @Autowired
    AquariumRepository aquariumRepository;

    @Autowired
    EventRepository eventRepository;


    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @PostMapping(value = "/api/addEvent")
    public Event addEvent(@RequestHeader("Authorization") String token, @RequestBody EventForm eventForm) {

        try {

            Aquarium aquarium = aquariumRepository.findById((eventForm.getAquariumId()));
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                Event event = eventForm.getEvent();
                event.setAquarium(aquarium);
                eventRepository.save(event);
                logger.info("Un nouvel évènement a été enregistré pour l'aquarium n° " + aquarium.getId());
                return event;
            }

        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @PostMapping(value = "/api/updateEvent")
    public Event updateEvent(@RequestHeader("Authorization") String token, @RequestBody EventForm eventForm) {
        try {
            Aquarium aquarium = aquariumRepository.findById(eventForm.getAquariumId());
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                Event event = eventForm.getEvent();
                event.setAquarium(aquarium);
                eventRepository.save(event);
                logger.info("L'évènement n° " + event.getId() + " a été mis à jour");
                return event;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/deleteEvent/{eventId}")
    public Event deleteEvent(@RequestHeader("Authorization") String token, @PathVariable int eventId) {
        try {
            Event event = eventRepository.findById(eventId);
            Member member = event.getAquarium().getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                eventRepository.delete(event);
                logger.info("L'évent N° " + eventId + " a été supprimé de la BDD");
                return event;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/deleteEventList/{aquariumId}")
    public List<Event> deleteEventList(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Event> events = eventRepository.findByAquariumOrderByDateDesc(aquarium);
                for (Event event : events){
                    eventRepository.delete(event);
                }

                logger.info("Les " + events.size() + " évènements de l'équarium n° " + aquariumId + " ont été supprimés de la BDD");
                return events;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }


    @GetMapping(value = "/api/getEventList/{aquariumId}")
    public List<Event> getEventList(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Event> events = eventRepository.findByAquariumOrderByDateDesc(aquarium);
                logger.info("Liste d'équipements envoyés pour l'aquarium n° " + aquariumId);
                return events ;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }



}
