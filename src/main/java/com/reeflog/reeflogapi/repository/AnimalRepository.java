package com.reeflog.reeflogapi.repository;

import com.reeflog.reeflogapi.beans.animals.Animal;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {

    List<Animal> findAnimalsByAquarium(Aquarium aquarium);


}
