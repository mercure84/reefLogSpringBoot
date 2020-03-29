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
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.*;
import java.util.stream.Collectors;

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


    @GetMapping("/api/downloadAquariumPicture/{aquariumId}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@RequestHeader("Authorization") String token,@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/api/uploadAquariumPicture/{aquariumId}")
    public void handleFileUpload(@RequestHeader("Authorization") String token, @RequestBody MultipartFile file, @PathVariable int aquariumId) {

        try {
            Aquarium aquarium = aquariumRepository.findById(aquariumId);
            Member member = aquarium.getMember();
            boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
            if (isTokenValide){
            storageService.store(file);
            File pictureToSave = new File("uploads/" +file.getOriginalFilename() );
            FileInputStream fis = new FileInputStream(pictureToSave);
            aquarium.setPicture(fis.readAllBytes());
            aquariumRepository.save(aquarium);
            logger.info("Un fichier a été uploadé");}

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





