package com.reeflog.reeflogapi.restcontroller;


import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.ReefAquarium;
import com.reeflog.reeflogapi.beans.helpers.ReefAquariumForm;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AquariumController {

    @Autowired
    AquariumRepository aquariumRepository;

    @Autowired
    MemberRepository memberRepository;


    @PostMapping(value = "/api/addNewReefAquarium")
    public ReefAquarium addNewReefAquarium(@RequestHeader("Authorization") String token, @RequestBody ReefAquariumForm reefAquariumForm){
        ReefAquarium newReefAquarium = new ReefAquarium();

        Member member = memberRepository.findById(reefAquariumForm.getMemberId());
        newReefAquarium.setMember(member);
        newReefAquarium.setName(reefAquariumForm.getName());
        newReefAquarium.setLength(reefAquariumForm.getLength());
        newReefAquarium.setHeight(reefAquariumForm.getHeight());
        newReefAquarium.setWidth(reefAquariumForm.getWidth());
        newReefAquarium.setSumpVolume(reefAquariumForm.getSumpVolume());
        newReefAquarium.setMainPopulation(reefAquariumForm.getMainPopulation());
        newReefAquarium.setTypeOfMaintenance(reefAquariumForm.getTypeOfMaintenance());
        aquariumRepository.save(newReefAquarium);
        return newReefAquarium;
    }
}
