package com.reeflog.reeflogapi.restcontroller;


import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Aquarium;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.ReefAquarium;
import com.reeflog.reeflogapi.beans.helpers.ReefAquariumForm;
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
public class AquariumController {

    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    AquariumRepository aquariumRepository;

    @Autowired
    MemberRepository memberRepository;


    @PostMapping(value = "/api/addNewReefAquarium")
    public ReefAquarium addNewReefAquarium(@RequestHeader("Authorization") String token, @RequestBody ReefAquariumForm reefAquariumForm) {
        ReefAquarium newReefAquarium = new ReefAquarium();

        Member member = memberRepository.findById(reefAquariumForm.getMemberId());
        newReefAquarium.setMember(member);
        newReefAquarium.setName(reefAquariumForm.getName());
        newReefAquarium.setLength(reefAquariumForm.getLength());
        newReefAquarium.setHeight(reefAquariumForm.getHeight());
        newReefAquarium.setWidth(reefAquariumForm.getWidth());
        newReefAquarium.setSumpVolume(reefAquariumForm.getSumpVolume());
        newReefAquarium.setMainPopulation(reefAquariumForm.getMainPopulation());
        newReefAquarium.setTypeOfMaintenance(reefAquariumForm.getTypeOfMaintenance());
        aquariumRepository.save(newReefAquarium);
        logger.info("Un nouvel aquarium a été enregistré " + newReefAquarium);
        return newReefAquarium;
    }

    @GetMapping(value = "/api/deleteAquarium/{id}")
    public String deleteAquarium(@RequestHeader("Authorization") String token, @PathVariable int id) {
        Aquarium aquariumToDelete = aquariumRepository.findById(id);
        aquariumRepository.delete(aquariumToDelete);
        return "L'aquarium " + aquariumToDelete.getId() + " a bien été supprimé de la base";
    }

    @GetMapping(value = "/api/getAquariumList/{memberId}")
    public List<Aquarium> getAquariumList(@RequestHeader("Authorization") String token, @PathVariable int memberId) {
        List<Aquarium> listAquariums = new ArrayList<>();
        try {
            Member member = memberRepository.findById(memberId);
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (!isTokenValide) {
                listAquariums = null;
            } else {
                listAquariums = aquariumRepository.findAquariumsByMember(member);
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
        logger.info("Envoi liste des aquariums : " + listAquariums);

        return listAquariums;
    }
    }
