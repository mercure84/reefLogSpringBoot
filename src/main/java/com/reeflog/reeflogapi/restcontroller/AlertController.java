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
import com.reeflog.reeflogapi.repository.WaterTestRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@RestController
public class AlertController {

    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));

    @Autowired
    AquariumRepository aquariumRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    WaterTestRepository waterTestRepository;

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

    //controller pour afficher les alertes actives qui doivent être montrées à l'utilisateur
    @GetMapping(value = "api/showAlerts/{aquariumId}")
    public Set<Alert> showAlerts(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {

        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                //on instancie une liste d'alerte vide qui sera remontée par le service
                Set<Alert> alertsToShow = new HashSet<>();

                //On récupère la liste des alertes actives
                Set<Alert> alerts = alertRepository.findAllByAquariumAndIsActiveTrue(aquarium);

                //pour chaque alerte on récupère la date butoir (targetDate)
                for (Alert alert : alerts) {
                    LocalDate targetLocalDate = LocalDate.now().minusDays(alert.getDayInterval());
                    Date targetDate = Date.from(targetLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                    //on cherche les tests qui sont postérieurs à cette date
                    List<WaterTest> waterTests = waterTestRepository.findByAquariumAndDateAfter(aquarium, targetDate);
                    //si je n'ai aucun test après la targetDate, je peux valider l'alerte comme positive et l'ajouter à ma liste alerToShow
                    if (waterTests.size() == 0) {

                        alertsToShow.add(alert);
                    }
                    else {
                    //sinon pour chaque test remonté, on regarde si on a une donné pour le paramètre de l'alerte
                    for (WaterTest test : waterTests) {
                        switch (alert.getTypeTest()) {
                            case PH:
                                if (test.getPh() == null) {
                                    alertsToShow.add(alert);
                                }
                                break;
                            case CALCIUM:
                                if (test.getCalcium() == null) {
                                    alertsToShow.add(alert);
                                }
                                break;
                            case ALCALINITY:
                                if (test.getAlcalinity() == null) {
                                    alertsToShow.add(alert);
                                }
                                break;
                            case SALINITY:
                                if (test.getSalinity() == null) {
                                    alertsToShow.add(alert);
                                }
                                break;
                            case SILICATES:
                                if (test.getSilicates() == null) {
                                    alertsToShow.add(alert);
                                }
                                break;
                            case NITRATES:
                                if (test.getNitrates() == null) {
                                    alertsToShow.add(alert);
                                }
                                break;
                            case NITRITES:
                                if (test.getNitrites() == null) {
                                    alertsToShow.add(alert);
                                }
                                break;
                            case AMMONIAC:
                                if (test.getAmmoniac() == null) {
                                    alertsToShow.add(alert);
                                }
                                break;
                            case MAGNESIUM:
                                if (test.getMagnesium() == null) {
                                    alertsToShow.add(alert);
                                }
                                break;
                            case TEMPERATURE:
                                if (test.getTemperature() == null) {
                                    alertsToShow.add(alert);
                                }
                                break;
                            case PHOSPHATES:
                                if (test.getPhosphates() == null) {
                                    alertsToShow.add(alert);
                                }
                                break;
                             }
                    }}
                }
                return alertsToShow;
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
