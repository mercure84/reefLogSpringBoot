package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.WaterTest;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.helpers.Measure;
import com.reeflog.reeflogapi.beans.helpers.WaterTestForm;
import com.reeflog.reeflogapi.beans.helpers.WaterTestGraph;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.repository.WaterTestRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WaterTestController {
    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    AquariumRepository aquariumRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    WaterTestRepository waterTestRepository;

    @PostMapping(value = "/api/addNewWaterTest")
    public WaterTest addNewWaterTest(@RequestHeader("Authorization") String token, @RequestBody WaterTestForm waterTestForm) {

        try {
            Aquarium aquarium = aquariumRepository.findById(waterTestForm.getAquariumId());
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                WaterTest waterTest = waterTestForm.getWaterTest();
                waterTest.setAquarium(aquarium);
                waterTestRepository.save(waterTest);
                logger.info("Un nouveau test d'eau a été ajouté pour l'aquarium n° " + aquarium.getId());
                return waterTest;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/getWaterTestList/{aquariumId}")
    public List<WaterTest> getWaterTestList(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<WaterTest> waterTestList = waterTestRepository.findByAquariumOrderByDateDesc(aquarium);
                logger.info("Envoi de la liste des tests d'eau pour l'aquarium n° " + aquariumId);
                return waterTestList;
            }

        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }


    @GetMapping(value = "/api/deleteWaterTest/{waterTestId}")
    public WaterTest deleteWaterTest(@RequestHeader("Authorization") String token, @PathVariable int waterTestId) {

        try {
            WaterTest waterTest = waterTestRepository.findById(waterTestId);
            Member member = waterTest.getAquarium().getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);

            if (isTokenValide) {
                waterTestRepository.delete(waterTest);
                logger.info("Le test n° " + waterTestId + " a été supprimé de la BDD");
                return waterTest;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }


    @GetMapping(value = "/api/deleteWaterTestList/{aquariumId}")
    public List<WaterTest> deleteWaterTestList(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {

        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {

                List<WaterTest> waterTests = waterTestRepository.findByAquariumOrderByDateDesc(aquarium);

                for (WaterTest waterTest : waterTests) {
                    waterTestRepository.delete(waterTest);
                }
                logger.info("Les " + waterTests.size() + " test(s) de l'aquarium n° " + aquariumId + "  ont été supprimés de la BDD");
                return waterTests;

            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @PostMapping(value = "/api/updateWaterTest")
    public WaterTest updateWaterTest(@RequestHeader("Authorization") String token, @RequestBody WaterTestForm waterTestForm) {

        try {
            WaterTest waterTest = waterTestForm.getWaterTest();
            Aquarium aquarium = aquariumRepository.findById(waterTestForm.getAquariumId());
            waterTest.setAquarium(aquarium);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                waterTestRepository.save(waterTest);
                logger.info("Le test n° " + waterTest + " a été mis à jour !");
                return waterTest;
            }

        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/getWaterTestsForGraph/{aquariumId}/{typeTest}")
    public WaterTestGraph getWaterTestsForGraph(@PathVariable int aquariumId, @PathVariable WaterTest.TypeTest typeTest) {

        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            List<WaterTest> waterTests = waterTestRepository.findByAquariumOrderByDateDesc(aquarium);
            WaterTestGraph graph = new WaterTestGraph();
            graph.setTypeTest(typeTest);
            List<Measure> measures = new ArrayList<>();

            for (WaterTest waterTest : waterTests
            ) {
                switch (typeTest) {
                    case PH:
                        if (waterTest.getPh() != null) {
                            measures.add((new Measure(waterTest.getDate(), waterTest.getPh())));
                        }
                        break;

                    case CALCIUM:
                        if (waterTest.getCalcium() != null) {
                            measures.add((new Measure(waterTest.getDate(), waterTest.getCalcium())));

                        }
                        break;

                    case AMMONIAC:
                        if (waterTest.getAmmoniac() != null) {
                            measures.add((new Measure(waterTest.getDate(), waterTest.getAmmoniac())));

                        }
                        break;

                    case NITRATES:
                        if (waterTest.getNitrates() != null) {
                            measures.add((new Measure(waterTest.getDate(), waterTest.getNitrates())));


                        }
                        break;

                    case NITRITES:
                        if (waterTest.getNitrites() != null) {
                            measures.add((new Measure(waterTest.getDate(), waterTest.getNitrites())));


                        }
                        break;

                    case SALINITY:
                        if (waterTest.getSalinity() != null) {
                            measures.add((new Measure(waterTest.getDate(), waterTest.getSalinity())));


                        }
                        break;

                    case MAGNESIUM:
                        if (waterTest.getMagnesium() != null) {
                            measures.add((new Measure(waterTest.getDate(), waterTest.getMagnesium())));


                        }
                        break;

                    case SILICATES:
                        if (waterTest.getSilicates() != null) {
                            measures.add((new Measure(waterTest.getDate(), waterTest.getSilicates())));


                        }
                        break;

                    case ALCALINITY:
                        if (waterTest.getAlcalinity() != null) {
                            measures.add((new Measure(waterTest.getDate(), waterTest.getAlcalinity())));
                        }
                        break;

                    case PHOSPHATES:
                        if (waterTest.getPhosphates() != null) {
                            measures.add((new Measure(waterTest.getDate(), waterTest.getPhosphates())));
                        }
                        break;

                    case TEMPERATURE:
                        if (waterTest.getTemperature() != null) {
                            measures.add((new Measure(waterTest.getDate(), waterTest.getTemperature())));
                        }
                        break;

                    default:
                        continue;
                }
            }


            graph.setMeasures(measures);
            return graph;

        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
        return null;


    }

}
