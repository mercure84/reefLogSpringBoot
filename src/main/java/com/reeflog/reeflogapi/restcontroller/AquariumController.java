package com.reeflog.reeflogapi.restcontroller;


import com.reeflog.reeflogapi.beans.ReefAquarium;
import com.reeflog.reeflogapi.beans.helpers.ReefAquariumForm;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AquariumController {

    @Autowired
    AquariumRepository aquariumRepository;


    @PostMapping(value = "/api/addNewReefAquarium")
    public ReefAquarium addNewReefAquarium(@RequestHeader("Authorization") String token, @RequestBody ReefAquariumForm ReefAquariumForm){
        ReefAquarium reefAquarium = new ReefAquarium();


        return reefAquarium;

    }





}
