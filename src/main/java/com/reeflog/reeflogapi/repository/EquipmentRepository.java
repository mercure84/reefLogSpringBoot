package com.reeflog.reeflogapi.repository;

import com.reeflog.reeflogapi.beans.Equipment;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

    List<Equipment> findByAquarium(Aquarium aquarium);
    Equipment findById(int equipmentId);

}
