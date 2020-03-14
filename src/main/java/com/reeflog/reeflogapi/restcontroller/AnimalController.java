package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.animals.Animal;
import com.reeflog.reeflogapi.beans.animals.fishes.Fish;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.helpers.FishForm;
import com.reeflog.reeflogapi.repository.AnimalRepository;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

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


}
