package com.reeflog.reeflogapi.repository;

import com.reeflog.reeflogapi.beans.animals.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalRepository extends JpaRepository<Animal, Integer> {
}
