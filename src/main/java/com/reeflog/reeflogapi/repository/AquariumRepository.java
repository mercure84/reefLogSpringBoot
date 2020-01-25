package com.reeflog.reeflogapi.repository;

import com.reeflog.reeflogapi.beans.Aquarium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AquariumRepository extends JpaRepository<Aquarium, Integer> {

}
