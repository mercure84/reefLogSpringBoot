package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Aquarium;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.WaterTest;
import com.reeflog.reeflogapi.beans.helpers.WaterTestForm;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.repository.WaterTestRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class WaterTestController {
    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    AquariumRepository aquariumRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    WaterTestRepository waterTestRepository;

    @PostMapping(value = "/api/addNewWaterTest")
    public WaterTest addNewWaterTest(@RequestHeader("Authorization") String token, @RequestBody WaterTestForm waterTestForm) {

        System.out.println(token);
        System.out.println(waterTestForm);
        try {
            Aquarium aquarium = aquariumRepository.findById(waterTestForm.getAquariumId());
            Member member = aquarium.getMember();
            // check token
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                WaterTest waterTest = waterTestForm.getWaterTest();
                waterTest.setDate(new Date());
                waterTest.setAquarium(aquarium);
                waterTestRepository.save(waterTest);
                return waterTest;

            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    @GetMapping(value = "/api/getWaterTestList/{aquariumId}")
    public List<WaterTest> getWaterTestList(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {
        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                return waterTestRepository.findByAquarium(aquarium);
            }

        } catch (Exception e) {

            return null;
        }
        return null;
    }


}
