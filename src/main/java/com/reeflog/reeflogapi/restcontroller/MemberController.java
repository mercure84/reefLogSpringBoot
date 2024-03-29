package com.reeflog.reeflogapi.restcontroller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.reeflog.reeflogapi.ReefLogApiApplication;
import com.reeflog.reeflogapi.beans.Member;
import com.reeflog.reeflogapi.beans.helpers.GoogleForm;
import com.reeflog.reeflogapi.beans.helpers.PasswordRecover;
import com.reeflog.reeflogapi.beans.helpers.SignUpForm;
import com.reeflog.reeflogapi.beans.helpers.ThemeMemberForm;
import com.reeflog.reeflogapi.exceptions.MemberException;
import com.reeflog.reeflogapi.repository.MemberRepository;
import com.reeflog.reeflogapi.repository.PasswordRecoverRepository;
import com.reeflog.reeflogapi.security.JwtAuthenticationController;
import com.reeflog.reeflogapi.security.JwtTokenUtil;
import com.reeflog.reeflogapi.utils.BeanValidator;
import com.reeflog.reeflogapi.utils.EmailService;
import com.reeflog.reeflogapi.utils.EncryptedPasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import static com.reeflog.reeflogapi.utils.EncryptedPasswordUtils.encryptePassword;

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

    @Autowired
    private PasswordRecoverRepository passwordRecoverRepository;

    @Autowired
    private JwtAuthenticationController jwtAuthenticationController;

    @Value("${webClientIdGoogleOAuth2}")
    String googleWebClientId;

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
            throw new MemberException("Cet utilisateur est déjà enregistré : changer d'E-mail et / ou de Pseudo ");
        } else {
            newMember.setUserName(signUpForm.getUserName());
            newMember.setEmail(signUpForm.getEmail());
            String encodedPassword = encryptePassword(signUpForm.getPassword());
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

    @PostMapping(value = "/api/setMemberTheme")
    public Member setMemberTheme(@RequestHeader("Authorization") String token, @RequestBody ThemeMemberForm themeMemberForm) {
        Member member = memberRepository.findByEmail(themeMemberForm.getEmail());
        boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
        if (isTokenValide) {
            member.setThemeColor(themeMemberForm.getThemeNumber());
            memberRepository.save(member);
            logger.info("Le thème couleur du membre " + member.getEmail() + " a été mis à jour : " + member.getThemeColor() );
            return member;
        } else {
            return null;
        }
    }


    @PostMapping(value = "/api/updateMember")
    public Member updateMember(@RequestHeader("Authorization") String token, @RequestBody SignUpForm signUpForm) throws RuntimeException, MemberException {
        Member member = memberRepository.findById(signUpForm.getIdToUpdate());
        boolean isTokenValide = jwtTokenUtil.validateCustomTokenForMember(token, member);
        if (isTokenValide && signUpForm.checkPassWord()) {
            if (member.getMemberStatus() == Member.MemberStatus.BLOCKED) {
                throw new MemberException("Ce membre ne peut être modifié, il a été verrouillé par un administrateur");
            } else {
                member.setRole("USER");
                member.setUserName(signUpForm.getUserName());
                member.setEmail(signUpForm.getEmail());
                String encodedPassword = encryptePassword(signUpForm.getPassword());
                member.setPassword(encodedPassword);
                memberRepository.save(member);
                logger.info("Le member " + member + " a été mis à jour ");
                return member;
            }
        } else {
            throw new RuntimeException("Problème de token ou de mot de passe qui ne correspondent pas");
        }
    }

    @Value("${server.url}")
    private String serverUrl;


    @GetMapping(value = "/api/recoverPassword/{email}")
    public void sendMailForPasswordRecover(@PathVariable String email){
        try {
            Member member = memberRepository.findByEmail(email);
            PasswordRecover passWordRecover = new PasswordRecover();
            passWordRecover.setMember(member);
            passWordRecover.setInitialDate(new Date());
            passWordRecover.setUrlToken();
            String linkRecover = this.serverUrl + "/web/recoverPasswordMail?token="+ passWordRecover.getUrlToken();
            String messageMember = "Cher " + member.getUserName().toUpperCase() + ",\n" + "Vous avez demandé la réinitialisation de votre mot de passe.\n" +
                    "Veuillez cliquer sur ce lien pour définir un nouveau mot de passe : \n \n " +
                    linkRecover + "\n \n" +
                    "L'équipe ReefLog";
            emailService.sendMail(member.getEmail(), "Réinitialisation de votre mot de passe " + member.getUserName().toUpperCase() + " SUR REEFLOG !", messageMember);
            passwordRecoverRepository.save(passWordRecover);
            logger.info("Un lien de mot de pass recovering a été envoyé à l'adresse " + email);

        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }

    // service d'authentification par google signin
    @PostMapping(value = "/api/oauth2/googleLogin")
    public GoogleForm googleMember(@RequestBody GoogleForm googleForm) throws GeneralSecurityException, IOException {
        try {
            System.out.println("Asking for Google OAuth2 : " + googleForm.getEmail() + ", id = " + googleForm.getTokenId());
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
        final HttpTransport transport = new NetHttpTransport();
        final JsonFactory jsonFactory = new GsonFactory();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(googleWebClientId))
                .build();
        GoogleIdToken idToken = verifier.verify(googleForm.getTokenId());
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();
            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User Google ID: " + userId);
            // Get profile information from payload
            String email = payload.getEmail();
            String givenName = (String) payload.get("given_name");

            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            System.out.println(email + " " + " vérifié ? " + emailVerified);
            Member member = memberRepository.findByEmail(email);
            if (member != null) {
                googleForm.setMember(member);
            } else {
                // si pas d'email dans la base des membres ==> instanciation d'un nouveau membre
                Member newGoogleMember = new Member();
                newGoogleMember.setEmail(email);
                UUID uuid = UUID.randomUUID();
                String randomUUIDString = uuid.toString();
                newGoogleMember.setPassword(encryptePassword(randomUUIDString.substring(3, 12)));
                newGoogleMember.setUserName(givenName);
                newGoogleMember.setSignupDate(new Date());
                memberRepository.save(newGoogleMember);
                logger.info("Création d'un nouveau membre via GoogleSignin : " + newGoogleMember);
                googleForm.setMember(newGoogleMember);
            }
            googleForm.setJwtToken(jwtAuthenticationController.createTokenForOAuth2(email));
            return googleForm;
        } else {
            System.out.println("Invalid ID token.");
            return null;
        }
    }

}