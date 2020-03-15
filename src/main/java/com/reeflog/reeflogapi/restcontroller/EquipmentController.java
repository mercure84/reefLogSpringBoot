package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Equipment;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.helpers.EquipmentForm;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.EquipmentRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EquipmentController {

    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));

    @Autowired
    AquariumRepository aquariumRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @PostMapping(value = "/api/addEquipment")
    public Equipment addEquipment(@RequestHeader("Authorization") String token, @RequestBody EquipmentForm equipmentForm) {
        try {
            Aquarium aquarium = aquariumRepository.findById(equipmentForm.getAquariumId());
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                Equipment equipment = equipmentForm.getEquipment();
                equipment.setAquarium(aquarium);
                equipmentRepository.save(equipment);
                logger.info("Un nouvel équipement a été ajouté : " + equipment);
                return equipment;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @PostMapping(value = "/api/updateEquipment")
    public Equipment updateEquipment(@RequestHeader("Authorization") String token, @RequestBody EquipmentForm equipmentForm) {
        try {
            Aquarium aquarium = aquariumRepository.findById(equipmentForm.getAquariumId());
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                Equipment equipment = equipmentForm.getEquipment();
                equipmentRepository.save(equipment);
                logger.info("L'équipement n° " + equipment.getId() + " a été mis à jour");
                return equipment;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/getEquipmentList/{aquariumid}")
    public List<Equipment> getEquipmentList(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Equipment> equipmentList = equipmentRepository.findByAquarium(aquarium);
                logger.info("Liste d'équipements envoyés pour l'aquarium n° " + aquariumId);
                return equipmentList ;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/getEquipment/{equipmentId}")
    public Equipment getEquipment(@RequestHeader("Authorization") String token, @PathVariable int equipmentId) {
        try {
            Equipment equipment = equipmentRepository.findById(equipmentId);
            Member member = equipment.getAquarium().getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                logger.info("Envoi de l'équipement n° " + equipmentId);
                return equipment;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/deleteEquipment/{equipmentId}")
    public Equipment deleteteEquipment(@RequestHeader("Authorization") String token, @PathVariable int equipmentId) {
        try {
            Equipment equipment = equipmentRepository.findById(equipmentId);
            Member member = equipment.getAquarium().getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                equipmentRepository.delete(equipment);
                logger.info("L'équipement N° " + equipmentId + " a été supprimé de la BDD");
                return equipment;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/deleteEquipmentList/{aquariumId}")
    public List<Equipment> deleteEquipmentList(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Equipment> equipmentList = equipmentRepository.findByAquarium(aquarium);
                for (Equipment equipment : equipmentList){
                    equipmentRepository.delete(equipment);
                }

                logger.info("Les " + equipmentList.size() + " équipements de l'équarium n° " + aquariumId + " ont été supprimés de la BDD");
                return equipmentList;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }


}
