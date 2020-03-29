package com.reeflog.reeflogapi;

import com.reeflog.reeflogapi.utils.storage.StorageProperties;
import com.reeflog.reeflogapi.utils.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ReefLogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReefLogApiApplication.class, args);
    }


    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }

}
