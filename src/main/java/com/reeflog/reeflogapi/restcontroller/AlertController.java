package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Alert;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.helpers.AlertForm;
import com.reeflog.reeflogapi.repository.AlertRepository;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlertController {

    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));

    @Autowired
    AquariumRepository aquariumRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AlertRepository alertRepository;

    @PostMapping(value = "/api/addOneAlert")
    public Alert addOneAlert(@RequestHeader("Authorization") String token, @RequestBody AlertForm alertForm) {
        try {
            Aquarium aquarium = aquariumRepository.findById(alertForm.getAquariumId());
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                Alert alert = alertForm.getAlerts().get(0);
                alert.setAquarium(aquarium);
                alertRepository.save(alert);
                logger.info("Une nouvelle alerte a été enregistrée " + alert);
                return alert;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @PostMapping(value = "/api/addAlertsCollection")
    public List<Alert> addAlertsCollection(@RequestHeader("Authorization") String token, @RequestBody AlertForm alertForm) {
        try {
            Aquarium aquarium = aquariumRepository.findById(alertForm.getAquariumId());
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Alert> alerts = alertForm.getAlerts();
                //on récupère la liste d'alertes déjà existantes sur l'aquarium ; on ne veut pas 2 alertes sur le même test pour le même aquarium
                //List<Alert> existingAlerts = alertRepository.findAllByAquarium(aquarium);
                for (Alert alert : alerts) {
                    alert.setAquarium(aquarium);

                    alertRepository.save(alert);
                }
                logger.info("Nous avons enregistré une liste de " + alerts.size() + " alerts, sur l'aquarium n° " + aquarium.getId());
                return alerts;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/getAlerts/{aquariumId}")
    public List<Alert> getAlerts(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Alert> alerts = alertRepository.findAllByAquarium(aquarium);
                logger.info("Une liste d'alerte a été retournée pour l'aquarium n° " + aquariumId);
                return alerts;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }


    @GetMapping(value = "/api/deleteAlerts/{aquariumId}")
    public List<Alert> deleteAlerts(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Alert> alerts = alertRepository.findAllByAquarium(aquarium);

                for (Alert alert : alerts) {

                    alertRepository.delete(alert);
                }
                logger.info("Les " + alerts.size() + " alertes de l'aquarium N° " + aquariumId + " ont té supprimées de la BDD");
                return alerts;
            }

        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;

    }
}
