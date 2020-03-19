package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.animals.Animal;
import com.reeflog.reeflogapi.beans.animals.corals.Anemone;
import com.reeflog.reeflogapi.beans.animals.corals.Lps;
import com.reeflog.reeflogapi.beans.animals.corals.Soft;
import com.reeflog.reeflogapi.beans.animals.corals.Sps;
import com.reeflog.reeflogapi.beans.animals.fishes.Fish;
import com.reeflog.reeflogapi.beans.animals.reefcleaners.*;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.helpers.AnimalForm;

import com.reeflog.reeflogapi.repository.AnimalRepository;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    @PostMapping(value = "/api/addAnimal")
    public Animal addAnimal(@RequestHeader("Authorization") String token, @RequestBody AnimalForm animalForm) {

        try {
            Animal animal = new Animal();
            Aquarium aquarium = aquariumRepository.findById(animalForm.getAquariumId());
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
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
                animal.setAquarium(aquarium);
                animalRepository.save(animal);
                logger.info("Un nouveau pensionnaire a été ajouté : " + animal);
                return animal;
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
        try {
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


    //controlleur qui permet d'alimenter les formulaires pour l'ajout de poissons, coraux, détritivores :=> envoie la liste des catégories de poissons, coraux, etc
    @GetMapping (value="/api/getAnimalSpecies")
    public Map<String, Enum<?>[]> getAnimalDataForm() {
        Map<String, Enum<?>[]> species = new HashMap<String, Enum<?>[]>();
        species.put("fish", Fish.FishSpecies.values());
        species.put("star", Star.StarSpecies.values());
        species.put("mollusk", Mollusk.MolluskSpecies.values());
        species.put("crustacean", Crustacean.CrustaceanSpecies.values());
        species.put("urchin", Urchin.UrchinSpecies.values());
        species.put("cucumber", Cucumber.CucumberSpecies.values());
        species.put("sps", Sps.SpsSpecies.values());
        species.put("lps", Lps.LpsSpecies.values());
        species.put("soft", Soft.SoftSpecies.values());
        species.put("anemone", Anemone.AnemoneSpecies.values());
        return species;

    }

    //controlleur qui permet d'alimenter les formulaires pour l'ajout de poissons, coraux, détritivores :=> envoie la liste des catégories de poissons, coraux, etc
    @GetMapping (value="/api/getAnimalSpecies/{animalKind}")
    public Map<String, Enum<?>[]> getAnimalDataFormByType(@PathVariable String animalKind) {
        Map<String, Enum<?>[]> species = new HashMap<String, Enum<?>[]>();
        switch(animalKind) {
            case "fish" :
                species.put("fish", Fish.FishSpecies.values());
                logger.info("Envoi liste de Species pour le type : FISH " );
                return species;
            case  "coral" :
                species.put("sps", Sps.SpsSpecies.values());
                species.put("lps", Lps.LpsSpecies.values());
                species.put("soft", Soft.SoftSpecies.values());
                species.put("anemone", Anemone.AnemoneSpecies.values());
                logger.info("Envoi liste de Species pour le type : CORAL " );

                return species;
            case "reefCleaner" :
                species.put("star", Star.StarSpecies.values());
                species.put("mollusk", Mollusk.MolluskSpecies.values());
                species.put("crustacean", Crustacean.CrustaceanSpecies.values());
                species.put("urchin", Urchin.UrchinSpecies.values());
                species.put("cucumber", Cucumber.CucumberSpecies.values());
                logger.info("Envoi liste de Species pour le type : RECIFALCLEANER " );

                return species;
            default :
                return null;
           }
    }

}
