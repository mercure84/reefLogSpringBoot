package com.reeflog.reeflogapi.restcontroller;

import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.exceptions.MemberException;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import com.reeflog.reeflogapi.utils.BeanValidator;
import com.reeflog.reeflogapi.utils.EmailService;
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
    EmailService emailService;

    @Autowired
    BeanValidator beanValidator;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping(value = "/api/addNewMember")
    public Member addNewMember(@RequestBody SignUpForm signUpForm) throws Exception {

        if (!beanValidator.isSignupFormValide(signUpForm)) {
            throw new MemberException("Le formulaire d'incription n'est pas valide ! " + signUpForm);
        }


        if (!signUpForm.getPassword().equals(signUpForm.getRepassword()))
            throw new MemberException(("You must confirm your password"));
        Member memberByEmail = memberRepository.findByEmail(signUpForm.getEmail());
        Member memberByUsername = memberRepository.findByUserName(signUpForm.getUserName());
        Member newMember = new Member();

        if (memberByEmail != null || memberByUsername != null) {
            throw new MemberException("Utilisateur déjà enregistré ! changer d'email ou de username");
        } else {
            newMember.setUserName(signUpForm.getUserName());
            newMember.setEmail(signUpForm.getEmail());
            String encodedPassword = EncryptedPasswordUtils.encryptePassword(signUpForm.getPassword());
            newMember.setPassword(encodedPassword);
            memberRepository.save(newMember);
            logger.info("Un nouveau membre a été ajouté : " + newMember);

            try {
                // envoi d'un mail à l'administrateur
                String messageAdmin = newMember.getUserName().toUpperCase() + " s'est enregistré sur REEFLOG le " + newMember.getSignupDate().toString() + ",  son email : " + newMember.getEmail();
                String emailAdmin = "julien.marcesse@gmail.com";
                emailService.sendMail(emailAdmin, newMember.getUserName().toUpperCase() + " vient de s'inscrire sur ReefLog !", messageAdmin);


                // envoi d'un mail au membre

                String messageMember = "Cher " + newMember.getUserName().toUpperCase() + ",\n" + "Nous vous souhaitons la bienvenue sur l'application ReefLog. Votre mot de passe a été crypté et sauvegardé dans notre base de données, vous pouvez le changer à tout moment dans la rubrique paramètre de votre application.\n" +
                        "Nous vous souhaitons une bonne utilisation, n'hésitez pas à nous remonter vos remarques et / ou bugs ! \n" +
                        "L'équipe ReefLog";
                emailService.sendMail(newMember.getEmail(), "BIENVENU " + newMember.getUserName().toUpperCase() + " SUR REEFLOG !", messageMember);
            } catch (Exception e) {

                logger.error("Problemes dans l'envoi des mails lors de la création d'un nouveau membre");
            }

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


    }

    @GetMapping(value = "/api/getMemberDetail/{email}")
    public Member getMemberDetail(@RequestHeader("Authorization") String token, @PathVariable String email) {
        Member member = memberRepository.findByEmail(email);
        boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
        if (isTokenValide) {
            logger.info("Envoi du détail du membre : " + member);
            return member;
        } else {
            return null;
        }


    }

    @PostMapping(value = "/api/updateMember")
    public Member updateMember(@RequestHeader("Authorization") String token, @RequestBody SignUpForm signUpForm) throws RuntimeException {
        Member member = memberRepository.findById(signUpForm.getIdToUpdate());
        boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
        if (isTokenValide && signUpForm.checkPassWord()) {
            if (member.getMemberStatus() == Member.MemberStatus.BLOCKED) {
                throw new RuntimeException("Ce membre ne peut être modifié, il a été verrouillé par un administrateur");

            } else {
                member.setRole("USER");
                member.setUserName(signUpForm.getUserName());
                member.setEmail(signUpForm.getEmail());
                String encodedPassword = EncryptedPasswordUtils.encryptePassword(signUpForm.getPassword());
                member.setPassword(encodedPassword);
                memberRepository.save(member);
                logger.info("Le member " + member + " a été mis à jour ");
                return member;
            }
        } else {
            throw new RuntimeException("Problème de token ou de mot de passe qui ne correspondent pas");
        }

    }
}