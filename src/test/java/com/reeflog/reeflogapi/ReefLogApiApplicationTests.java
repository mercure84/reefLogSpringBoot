package com.reeflog.reeflogapi;

import com.reeflog.reeflogapi.beans.animals.Animal;
import com.reeflog.reeflogapi.beans.animals.corals.Anemone;
import com.reeflog.reeflogapi.beans.animals.corals.Coral;
import com.reeflog.reeflogapi.beans.animals.fishes.Fish;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.utils.BuildInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReefLogApiApplicationTests {

    @Autowired
    BuildInfoService buildInfoService;

    @Autowired
    AquariumRepository aquariumRepository;

    @Test
    public void NoTest() {

        System.out.println("Hello World, there is no tests here !");
        System.out.println("Application name : " + buildInfoService.getApplicationName() + ", version = " + buildInfoService.getBuildVersion());

        Fish fish = new Fish();
        fish.setSex(Fish.Sexe.FEMALE);
        fish.setFishSpecies(Fish.FishSpecies.AMPHIPRION);
        fish.setName("Ocellaris");
        fish.setQuantity(1);
        fish.setCurrentSize(Animal.CurrentSize.M);
        fish.setNotes("acheté au Poisson d'Or");
        System.out.println("Poisson construit = " + fish.toString() + " " + fish.getFishSpecies() + " " + fish.getName() + fish.getNotes());



        Coral coral = new Coral();
        coral.setQuantity(1);
        coral.setCurrentSize(Animal.CurrentSize.L);
        coral.setNotes("reçu en bouture dans une bourse");
        ((Anemone) coral).setAnemoneSpecies(Anemone.AnemoneSpecies.ENTACMEA);





    }


}
