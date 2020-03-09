package com.reeflog.reeflogapi.repository;

import com.reeflog.reeflogapi.beans.Aquarium;
import com.reeflog.reeflogapi.beans.WaterTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WaterTestRepository extends JpaRepository<WaterTest, Integer> {

List<WaterTest> findByAquarium(Aquarium aquarium);

}
