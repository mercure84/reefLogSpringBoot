package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.animals.Animal;
import com.reeflog.reeflogapi.beans.animals.corals.*;
import com.reeflog.reeflogapi.beans.animals.fishes.Fish;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.helpers.CoralForm;
import com.reeflog.reeflogapi.beans.helpers.FishForm;
import com.reeflog.reeflogapi.repository.AnimalRepository;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnimalController {

    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));


    @Autowired
    AnimalRepository animalRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    AquariumRepository aquariumRepository;


    @PostMapping(value = "/api/addFish")
    public Animal addFish(@RequestHeader("Authorization") String token, @RequestBody FishForm fishForm) {
        Aquarium aquarium = aquariumRepository.findById(fishForm.getAquariumId());
        Member member = aquarium.getMember();
        try {
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                Fish fish = fishForm.getFish();
                fish.setAquarium(aquarium);
                animalRepository.save(fish);
                logger.info("Un nouveau pentionnaire FISH a été ajouté : " + fish);
                return fish;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/getCoralTest")
    public Coral getCoral() {
        Anemone anemone = new Anemone();
        anemone.setName("TestName");
        anemone.setNotes("Test de notes bla bla bla");
        anemone.setCurrentSize(Animal.CurrentSize.S);
        anemone.setQuantity(1);
        anemone.setAnemoneSpecies(Anemone.AnemoneSpecies.ENTACMEA);
        return anemone;

    }


    @PostMapping(value = "/api/addCoral")
    public Animal addCoral(@RequestHeader("Authorization") String token, @RequestBody CoralForm coralForm) {
        Aquarium aquarium = aquariumRepository.findById(coralForm.getAquariumId());
        Member member = aquarium.getMember();
        try {
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                Coral coral = new Coral();
                if (coralForm.getAnemone() != null) {
                    coral = coralForm.getAnemone();
                } else if (coralForm.getLps() != null) {
                    coral = coralForm.getLps();
                } else if (coralForm.getSoft() != null){
                    coral = coralForm.getSoft();
                } else if (coralForm.getSps()!= null){
                    coral = coralForm.getSps();
                }

                coral.setAquarium(aquarium);
                animalRepository.save(coral);
                logger.info("Un nouveau pensionnaire CORAL a été ajouté : " + coral);
                return coral;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }


}
