package com.reeflog.reeflogapi.restcontroller;


import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.aquariums.ReefAquarium;
import com.reeflog.reeflogapi.beans.helpers.ReefAquariumForm;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AquariumController {

    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AquariumRepository aquariumRepository;

    @Autowired
    MemberRepository memberRepository;


    @PostMapping(value = "/api/addNewReefAquarium")
    public ReefAquarium addNewReefAquarium(@RequestHeader("Authorization") String token, @RequestBody ReefAquariumForm reefAquariumForm) {

        try {
            ReefAquarium newReefAquarium =  reefAquariumForm.getReefAquarium();
            Member member = memberRepository.findById(reefAquariumForm.getMemberId());
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide && aquariumRepository.findAquariumsByMember(member).size() == 0) {
                newReefAquarium.setMember(member);
                aquariumRepository.save(newReefAquarium);
                logger.info("Un nouvel aquarium a été enregistré " + newReefAquarium);
                return newReefAquarium;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/deleteAquarium/{id}")
    public String deleteAquarium(@RequestHeader("Authorization") String token, @PathVariable int id) {
        try {
            Aquarium aquarium = aquariumRepository.findById(id);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                aquariumRepository.delete(aquarium);
                return "L'aquarium " + aquarium.getId() + " a bien été supprimé de la base";
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;

    }

    @GetMapping(value = "/api/getAquariumList/{memberId}")
    public List<Aquarium> getAquariumList(@RequestHeader("Authorization") String token, @PathVariable int memberId) {
        try {
            Member member = memberRepository.findById(memberId);
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Aquarium> listAquariums = aquariumRepository.findAquariumsByMember(member);
                logger.info("Envoi de la liste des aquariums : pour le membre " + memberId + " : " + listAquariums.size() + " aquarium(s) trouvés !");
                return listAquariums;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
        return null;
    }

    @GetMapping(value = "/api/getAquariumDetail/{aquariumId}")
    public Aquarium getAquariumDetail(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                return aquarium ;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
        return null;
    }


    @PostMapping(value = "/api/updateReefAquarium")
    public Aquarium updateReefAquarium(@RequestHeader("Authorization") String token, @RequestBody ReefAquariumForm reefAquariumForm) {
        try {
            ReefAquarium newReefAquarium =  reefAquariumForm.getReefAquarium();
            Member member = memberRepository.findById(reefAquariumForm.getMemberId());
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                newReefAquarium.setMember(member);
                aquariumRepository.save(newReefAquarium);

                logger.info("L'aquarium n° " + newReefAquarium.getId() + " a été mis à jour");
                return newReefAquarium;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }


    @GetMapping(value = "/api/getAllAquariums")
    public List<Aquarium> getAllAquariums (){
        try {
            List<Aquarium> aquariums = aquariumRepository.findAll();
            logger.info("Envoi de la liste complètes des aquariums");
            return aquariums;
        } catch(Exception e){
                        logger.error(String.valueOf(e));
                        return null;
        }

    }
}
