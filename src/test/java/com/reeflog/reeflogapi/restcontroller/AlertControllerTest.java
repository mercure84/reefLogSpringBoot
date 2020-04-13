package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.beans.Alert;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.WaterTest;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.beans.aquariums.ReefAquarium;
import com.reeflog.reeflogapi.beans.helpers.AlertForm;
import com.reeflog.reeflogapi.repository.AlertRepository;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class AlertControllerTest {

    @InjectMocks
    AlertController alertController;

    @Mock
    AlertRepository alertRepository;

    @Mock
    JwtTokenUtil jwtTokenUtil;

    @Mock
    AquariumRepository aquariumRepository;


    @Test
    public void addOneAlertTest(){

        List<Alert> alerts = new ArrayList<>();
        Alert alert = new Alert();
        alert.setTypeTest(WaterTest.TypeTest.PH);
        AlertForm alertForm = new AlertForm();
        alertForm.setAlerts(alerts);
        Aquarium aquarium = new ReefAquarium();
        String token = "blablaToken";
        Member member = aquarium.getMember();
        Alert existingAlert = new Alert();
        existingAlert.setTypeTest(WaterTest.TypeTest.PH);
        when(aquariumRepository.findById(alertForm.getAquariumId())).thenReturn(aquarium);
        when(jwtTokenUtil.validateCustomTokenForMember(token, member)).thenReturn(true);
        alertController.addOneAlert(token, alertForm);

    }





}
