package com.reeflog.reeflogapi.repository;

import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.WaterTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface WaterTestRepository extends JpaRepository<WaterTest, Integer> {

List<WaterTest> findByAquariumOrderByDateDesc(Aquarium aquarium);
WaterTest findById(int id);

List<WaterTest> findByAquariumAndDateAfterAndAlcalinityIsNotNull(Aquarium aquarium, Date date);
    List<WaterTest> findByAquariumAndDateAfterAndSalinityIsNotNull(Aquarium aquarium, Date date);

    List<WaterTest> findByAquariumAndDateAfterAndNitratesIsNotNull(Aquarium aquarium, Date date);

    List<WaterTest> findByAquariumAndDateAfterAndNitritesIsNotNull(Aquarium aquarium, Date date);

    List<WaterTest> findByAquariumAndDateAfterAndAmmoniacIsNotNull(Aquarium aquarium, Date date);

    List<WaterTest> findByAquariumAndDateAfterAndSilicatesIsNotNull(Aquarium aquarium, Date date);

    List<WaterTest> findByAquariumAndDateAfterAndTemperatureIsNotNull(Aquarium aquarium, Date date);

    List<WaterTest> findByAquariumAndDateAfterAndCalciumIsNotNull(Aquarium aquarium, Date date);

    List<WaterTest> findByAquariumAndDateAfterAndMagnesiumIsNotNull(Aquarium aquarium, Date date);

    List<WaterTest> findByAquariumAndDateAfterAndPhIsNotNull(Aquarium aquarium, Date date);

    List<WaterTest> findByAquariumAndDateAfterAndPhosphatesIsNotNull(Aquarium aquarium, Date date);









}
