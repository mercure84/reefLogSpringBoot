package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.animals.Animal;
import com.reeflog.reeflogapi.beans.animals.Fish;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.helpers.AnimalForm;
import com.reeflog.reeflogapi.beans.helpers.CountingForm;
import com.reeflog.reeflogapi.repository.AnimalRepository;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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


    @PostMapping(value = "/api/addAnimal/Fish")
    public Animal addAnimal(@RequestHeader("Authorization") String token, @RequestBody AnimalForm animalForm) {

        try {
            Fish fish = animalForm.getFish();
            Aquarium aquarium = aquariumRepository.findById(animalForm.getAquariumId());
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                fish.setAquarium(aquarium);
                animalRepository.save(fish);
                logger.info("Un nouveau pensionnaire a été ajouté : " + fish);
                return fish;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/deleteAnimals/{aquariumId}")
    public List<Animal> deleteAnimals(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Animal> animals = animalRepository.findAnimalsByAquariumOrderByIncomingDateDesc(aquarium);
                animalRepository.deleteAll(animals);
                logger.info("Les " + animals.size() + " pensionnaires de l'aquarium n° " + aquariumId + " ont été supprimés de la BDD");
                return animals;

            }

        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;

        }

        return null;

    }

    @GetMapping(value = "/api/deleteAnimal/{animalId}")
    public Animal deleteOneAnimal(@RequestHeader("Authorization") String token, @PathVariable int animalId) {
        try {
            Animal animal = animalRepository.findById(animalId);
            Member member = animal.getAquarium().getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                animalRepository.delete(animal);

                logger.info("Le pensionnaire " + animal + " a été supprimé de la BDD");
                return animal;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @PostMapping(value = "/api/updateAnimal/Fish")
    public Animal updateAnimal(@RequestHeader("Authorization") String token, @RequestBody AnimalForm animalForm) {
        try {
            Fish animal = animalForm.getFish();
            Aquarium aquarium = aquariumRepository.findById(animalForm.getAquariumId());
            animal.setAquarium(aquarium);
            Member member = aquarium.getMember();

            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                animalRepository.save(animal);
                logger.info("Le pensionnaire  " + animal + " a été mis à jour !");
                return animal;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/getAnimals/{aquariumId}")
    public List<Animal> getAnimals(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Animal> animals = animalRepository.findAnimalsByAquariumOrderByIncomingDateDesc(aquarium);
                logger.info("Liste d'animaux envoyés pour l'aquarium n°" + aquariumId);
                return animals;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/getAnimals/Fishes/{aquariumId}")
    public List<Fish> getFishes(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Animal> animals = animalRepository.findAnimalsByAquariumOrderByIncomingDateDesc(aquarium);
                List<Fish> fishes = new ArrayList<>();
                animals.forEach(animal -> {
                    if (animal instanceof Fish) {
                        fishes.add((Fish) animal);
                    }
                });
                logger.info("Fishes envoyés pour l'aquarium n°" + aquariumId + ",  ==> " + fishes);
                return fishes;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/getAnimal/{animalId}")
    public Animal getAnimal(@RequestHeader("Authorization") String token, @PathVariable int animalId) {

        try {
            Animal animal = animalRepository.findById(animalId);
            Member member = animal.getAquarium().getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                logger.info("Animal n° " + animalId + " envoyé");
                return animal;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }

    @PostMapping(value = "/api/countFishes")
    public CountingForm countFishes(@RequestHeader("Authorization") String token, @RequestBody CountingForm countingForm) {
        try {
            Aquarium aquarium = aquariumRepository.findById(countingForm.getAquariumId());
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                countingForm.getFishIds().forEach(id -> {
                    Optional<Animal> optionalAnimal = animalRepository.findById(id);
                    optionalAnimal.ifPresent(animal -> {
                        animal.setLastPresenceDate(new Date());
                        animalRepository.save(animal);
                    });
                });

            }
            System.out.println("Fish count ===> " + countingForm);
            return countingForm;
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
    }


}
