package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.animals.Animal;
import com.reeflog.reeflogapi.beans.animals.corals.*;
import com.reeflog.reeflogapi.beans.animals.fishes.Fish;
import com.reeflog.reeflogapi.beans.animals.reefcleaners.ReefCleaner;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.helpers.animals.AnimalForm;
import com.reeflog.reeflogapi.beans.helpers.animals.CoralForm;
import com.reeflog.reeflogapi.beans.helpers.animals.FishForm;
import com.reeflog.reeflogapi.beans.helpers.animals.ReefCleanerForm;
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
                } else if (coralForm.getSoft() != null) {
                    coral = coralForm.getSoft();
                } else if (coralForm.getSps() != null) {
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
                ReefCleaner reefCleaner = new ReefCleaner();
                if (reefCleanerForm.getCrustacean() != null) {
                    reefCleaner = reefCleanerForm.getCrustacean();
                } else if (reefCleanerForm.getCucumber() != null) {
                    reefCleaner = reefCleanerForm.getCucumber();
                } else if (reefCleanerForm.getMollusk() != null) {
                    reefCleaner = reefCleanerForm.getMollusk();
                } else if (reefCleanerForm.getStar() != null) {
                    reefCleaner = reefCleanerForm.getStar();
                } else if (reefCleanerForm.getUrchin() != null) {
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


    @GetMapping(value = "/api/deleteAnimals/{aquariumId}")
    public List<Animal> deleteAnimals(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Animal> animals = animalRepository.findAnimalsByAquarium(aquarium);

                for (Animal animal : animals) {
                    animalRepository.delete(animal);
                }
                logger.info("Les " + animals.size() + " pensionnaires de l'aquarium n° " + aquariumId + " ont été supprimés de la BDD");
                return animals;

            }

        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;

        }

        return null;

    }

    @GetMapping(value = "/api/deleteOneAnimal/{animalId}")
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

    @PostMapping(value = "/api/updateAnimal")
    public Animal updateAnimal(@RequestHeader("Authorization") String token, @RequestBody AnimalForm animalForm) {
        Animal animal = new Animal();

        if (animalForm.getAnemone() != null) {
            animal = animalForm.getAnemone();
        }
        if (animalForm.getCrustacean() != null) {
            animal = animalForm.getCrustacean();
        }
        if (animalForm.getCucumber() != null) {
            animal = animalForm.getCucumber();
        }
        if (animalForm.getFish() != null) {
            animal = animalForm.getFish();
        }
        if (animalForm.getLps() != null) {
            animal = animalForm.getLps();
        }
        if (animalForm.getSoft() != null) {
            animal = animalForm.getSoft();
        }
        if (animalForm.getSps() != null) {
            animal = animalForm.getSps();
        }
        if (animalForm.getMollusk() != null) {
            animal = animalForm.getMollusk();
        }
        if (animalForm.getStar() != null) {
            animal = animalForm.getStar();
        }
        if (animalForm.getUrchin() != null) {
            animal = animalForm.getUrchin();
        }

        Aquarium aquarium = aquariumRepository.findById(animalForm.getAquariumId());
        animal.setAquarium(aquarium);
        Member member = aquarium.getMember();
        try {
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


    @GetMapping(value="/api/getAnimals/{aquariumId}")
    public List<Animal> getAnimals(@RequestHeader("Authorization") String token, @PathVariable int aquariumId){
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                List<Animal> animals = animalRepository.findAnimalsByAquarium(aquarium);
                logger.info("Liste d'animaux envoyés pour l'aquarium n°" + aquariumId);
                return animals;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
            return null;
        }
        return null;
    }


    @GetMapping(value="/api/getAnimal/{animalId}")
    public Animal getAnimal(@RequestHeader("Authorization") String token, @PathVariable int animalId){

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


}
