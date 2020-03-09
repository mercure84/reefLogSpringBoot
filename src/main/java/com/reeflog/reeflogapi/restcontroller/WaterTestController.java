package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.WaterTest;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WaterTestController {
    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    AquariumRepository aquariumRepository;

    @Autowired
    MemberRepository memberRepository;

    @PostMapping(value="/api/addNewWaterTest")
    public WaterTest addNewWaterTest(@RequestHeader("Authorization") String token, @RequestBody WaterTest waterTest){

        return null;


    }




}
