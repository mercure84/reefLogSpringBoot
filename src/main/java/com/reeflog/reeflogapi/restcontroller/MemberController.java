package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import com.reeflog.reeflogapi.utils.EncryptedPasswordUtils;
import com.reeflog.reeflogapi.beans.helpers.SignUpForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MemberController {

    private static final Logger logger = LoggerFactory.getLogger((ReefLogApiApplication.class));


    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/api/addNewMember")
    public Member addNewMember(@RequestBody SignUpForm signUpForm) throws RuntimeException {
        if (!signUpForm.getPassword().equals(signUpForm.getRepassword()))
            throw new RuntimeException(("You must confirm your password"));
        Member memberByEmail = memberRepository.findByEmail(signUpForm.getEmail());
        Member memberByUsername = memberRepository.findByUserName(signUpForm.getUserName());
        Member newMember = new Member();

        if (memberByEmail != null || memberByUsername != null) {
            throw new RuntimeException("Utilisateur déjà enregistré ! changer d'email ou de username");
        } else {
            newMember.setFirstName(signUpForm.getFirstName());
            newMember.setLastName(signUpForm.getLastName());
            newMember.setUserName(signUpForm.getUserName());
            newMember.setEmail(signUpForm.getEmail());
            String encodedPassword = EncryptedPasswordUtils.encryptePassword(signUpForm.getPassword());
            newMember.setPassword(encodedPassword);
            memberRepository.save(newMember);
            logger.info("Un nouveau membre a été ajouté : " + newMember);

        }

        return newMember;
    }


    @GetMapping(value = "/api/deleteMember/{id}")
    public Member deleteMember(@RequestHeader("Authorization") String token, @PathVariable int id) {
        Member memberToDelete = memberRepository.findById(id);
        boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, memberToDelete);
        if (isTokenValide) {
            memberRepository.delete(memberToDelete);
            logger.info("Le membre suivant a été supprimé : " + memberToDelete);

        } else {
            logger.info("Le membre suivant n'a pas été supprimé : " + memberToDelete + " à cause d'un problème de token");
            memberToDelete = null;
        }
        return memberToDelete;


    }}