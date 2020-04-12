package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Alert;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.WaterTest;
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

import java.util.ArrayList;
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

                Alert checkAlert = alertRepository.findByAquariumAndAndTypeTest(aquarium, alert.getTypeTest());
                if (checkAlert != null) {
                    alert.setId(checkAlert.getId());
                }

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
                List<Alert> alertsData = saveAlertHelper(alerts, aquarium);
                logger.info("Nous avons enregistré une liste de " + alertsData.size() + " alerts, sur l'aquarium n° " + aquarium.getId());
                return alertsData;
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
                //si le nombre d'alertes est null ou < au nombre d'alertes possible, on instancie une liste d'alertes inactives
                boolean isAlertsEmpty = alerts.size() == 0 ;
                if (alerts.size() < WaterTest.TypeTest.values().length) {
                    List<Alert> missingAlerts = new ArrayList<>();
                    for (WaterTest.TypeTest type : WaterTest.TypeTest.values()) {
                        if (isAlertsEmpty){
                            Alert newAlert = new Alert();
                            newAlert.setAquarium(aquarium);
                            newAlert.setTypeTest(type);
                            missingAlerts.add(newAlert);

                        } else {

                        for (Alert alert : alerts) {
                            if (!alert.getTypeTest().equals(type)) {
                                Alert newAlert = new Alert();
                                newAlert.setAquarium(aquarium);
                                newAlert.setTypeTest(type);
                                missingAlerts.add(newAlert);
                            }
                        }}
                    }
                   alerts = saveAlertHelper(missingAlerts, aquarium);
                }

                logger.info("Une liste de " + alerts.size() + " alertes a été retournée pour l'aquarium n° " + aquariumId);
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
                logger.info("Les " + alerts.size() + " alertes de l'aquarium N° " + aquariumId + " ont été supprimées de la BDD");
                return alerts;
            }

        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;

    }

    private List<Alert> saveAlertHelper(List<Alert> alerts, Aquarium aquarium) {
        for (Alert alert : alerts) {
            //pour chaque alerte à enregistrer on regarde si une alerte existe avec la concordance aquarium / TypeTest
            Alert checkAlert = alertRepository.findByAquariumAndAndTypeTest(aquarium, alert.getTypeTest());
            //si on a un résultat on regarde si le TypeTest est équivalent, si oui on set l'iD pour faire un save-update
            if (checkAlert != null && checkAlert.getTypeTest().equals(alert.getTypeTest())) {
                alert.setId(checkAlert.getId());
            }
            alert.setAquarium(aquarium);
            alertRepository.save(alert);
        }
        return alertRepository.findAllByAquarium(aquarium);
    }

}
