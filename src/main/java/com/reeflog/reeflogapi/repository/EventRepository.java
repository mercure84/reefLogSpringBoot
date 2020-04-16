package com.reeflog.reeflogapi.repository;

import com.reeflog.reeflogapi.beans.Event;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

Event findById(int eventId);
List<Event> findByAquarium(Aquarium aquarium);

}
