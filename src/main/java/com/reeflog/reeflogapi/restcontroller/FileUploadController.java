package com.reeflog.reeflogapi.restcontroller;


import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.aquariums.Aquarium;
import com.reeflog.reeflogapi.repository.AquariumRepository;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import com.reeflog.reeflogapi.utils.storage.StorageFileNotFoundException;
import com.reeflog.reeflogapi.utils.storage.StorageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;


@RestController
public class FileUploadController {

    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));

    private final StorageService storageService;

    @Autowired
    AquariumRepository aquariumRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }


    @GetMapping(value = "/api/downloadAquariumPicture/{aquariumId}", produces = MediaType.IMAGE_PNG_VALUE)
    @ResponseBody
    public byte[] downloadAquariumPicture(@RequestHeader("Authorization") String token, @PathVariable int aquariumId) {

        Aquarium aquarium = aquariumRepository.findById(aquariumId);
        Member member = aquarium.getMember();
        boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
        try {
            if (isTokenValide) {
                byte[] picture = aquarium.getPicture();
                return picture;
            }
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
        return null;

    }

    @PostMapping("/api/uploadAquariumPicture/{aquariumId}")
    public void uploadAquariumPicture(@RequestHeader("Authorization") String token, @RequestBody MultipartFile file, @PathVariable int aquariumId) {

        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide) {
                storageService.store(file);
                File pictureToSave = new File("uploads/" + file.getOriginalFilename());
                FileInputStream fis = new FileInputStream(pictureToSave);
                aquarium.setPicture(fis.readAllBytes());
                aquariumRepository.save(aquarium);
                fis.close();
                Files.delete(Paths.get(pictureToSave.getPath()));
            logger.info("Un fichier a été uploadé et sauvé dans la BDD");
            }

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }


}





