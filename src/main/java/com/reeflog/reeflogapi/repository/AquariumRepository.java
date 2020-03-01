package com.reeflog.reeflogapi.repository;

import com.reeflog.reeflogapi.beans.Aquarium;
import com.reeflog.reeflogapi.beans.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AquariumRepository extends JpaRepository<Aquarium, Integer> {

    Aquarium findById(int id);

    List<Aquarium> findAquariumsByMember(Member member);

}
