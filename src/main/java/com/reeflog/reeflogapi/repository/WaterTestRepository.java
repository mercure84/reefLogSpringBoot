package com.reeflog.reeflogapi.repository;

import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.WaterTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface WaterTestRepository extends JpaRepository<WaterTest, Integer> {

List<WaterTest> findByAquariumOrderByDateDesc(Aquarium aquarium);
WaterTest findById(int id);

List<WaterTest> findByAquariumAndDateAfter(Aquarium aquarium, Date date);



}
