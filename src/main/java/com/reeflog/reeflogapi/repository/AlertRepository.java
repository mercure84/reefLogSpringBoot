package com.reeflog.reeflogapi.repository;

import com.reeflog.reeflogapi.beans.Alert;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Integer> {

List<Alert> findAllByAquarium (Aquarium aquarium);

Alert findByAquariumAndAndTypeTest(Aquarium aquarium, Alert.TypeTest typeTest);


}
