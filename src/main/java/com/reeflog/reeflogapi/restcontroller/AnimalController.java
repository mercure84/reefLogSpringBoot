package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.animals.Animal;
import com.reeflog.reeflogapi.beans.animals.corals.*;
import com.reeflog.reeflogapi.beans.animals.fishes.Fish;
import com.reeflog.reeflogapi.beans.animals.reefcleaners.ReefCleaner;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.helpers.CoralForm;
import com.reeflog.reeflogapi.beans.helpers.FishForm;
import com.reeflog.reeflogapi.beans.helpers.ReefCleanerForm;
import com.reeflog.reeflogapi.repository.AnimalRepository;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(value = "/api/addReefCleaner")
    public Animal addReefCleaner(@RequestHeader("Authorization") String token, @RequestBody ReefCleanerForm reefCleanerForm) {
        Aquarium aquarium = aquariumRepository.findById(reefCleanerForm.getAquariumId());
        Member member = aquarium.getMember();
        try {
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                ReefCleaner reefCleaner= new ReefCleaner();
                if (reefCleanerForm.getCrustacean() != null) {
                    reefCleaner = reefCleanerForm.getCrustacean();
                } else if (reefCleanerForm.getCucumber() != null) {
                    reefCleaner = reefCleanerForm.getCucumber();
                } else if (reefCleanerForm.getMollusk() != null){
                    reefCleaner = reefCleanerForm.getMollusk();
                } else if (reefCleanerForm.getStar()!= null){
                    reefCleaner = reefCleanerForm.getStar();
                } else if (reefCleanerForm.getUrchin()!= null){
                    reefCleaner = reefCleanerForm.getUrchin();
                }
                reefCleaner.setAquarium(aquarium);
                animalRepository.save(reefCleaner);
                logger.info("Un nouveau pensionnaire REEFCLEAN a été ajouté : " + reefCleaner);
                return reefCleaner;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }


    @GetMapping(value="/api/deleteAnimals/{aquariumId}")
    public List<Animal> deleteAnimals (@RequestHeader("Authorization") String token, @PathVariable int aquariumId){
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Animal> animals = animalRepository.findAnimalsByAquarium(aquarium);

                for (Animal animal : animals){
                    animalRepository.delete(animal);
                }
                logger.info("Les " + animals.size() + " pensionnaires de l'aquarium n° " + aquariumId + " ont été supprimés de la BDD");
                return animals;

            }

        } catch (Exception e){
            logger.error(String.valueOf(e));
            return null;

        }

        return null;

    }

    @GetMapping(value="/api/deleteOneAnimal/{animalId}")
    public Animal deleteOneAnimal (@RequestHeader("Authorization") String token, @PathVariable int animalId){
        try {
            Animal animal = animalRepository.findById(animalId);
            Member member = animal.getAquarium().getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                    animalRepository.delete(animal);

                logger.info("Le pensionnaire " + animal + " a été supprimé de la BDD");
                return animal;

            }

        } catch (Exception e){
            logger.error(String.valueOf(e));
            return null;

        }

        return null;

    }





}
