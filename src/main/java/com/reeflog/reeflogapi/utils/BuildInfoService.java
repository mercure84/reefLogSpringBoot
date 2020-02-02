package com.reeflog.reeflogapi.utils;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Data
@Service
public class BuildInfoService {

    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));


    @Value("${application.name}")
    private String applicationName;

    @Value("${build.version}")
    private String buildVersion;

    @PostConstruct
    public void infoBuild() {

        logger.info("L'application " + this.applicationName + " a été construite dans sa version : " + this.buildVersion);


    }


}
