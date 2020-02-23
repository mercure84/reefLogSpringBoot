package com.reeflog.reeflogapi.security;

import com.reeflog.reeflogapi.beans.helpers.TokenJWTHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// gère l'API de login  ==> si l'authent est OK on utilise JwtTokenUtil pour créer un token
@RestController
public class JwtAuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationController.class);


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        logger.info("[REST] demande de token pour " + authenticationRequest.getUsername());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }


    // service utilitaire à destination de l'API : permet d'avoir un retour sur la validité du token
    @RequestMapping(value = "/api/checkToken", method = RequestMethod.POST)
    public TokenJWTHelper checkToken(@RequestBody TokenJWTHelper tokenJWTHelper) {
        boolean isTokenValide = false;
        String email = tokenJWTHelper.getEmail();
        String token = tokenJWTHelper.getToken().substring(7);
        try {
            String emailFromToken = jwtTokenUtil.getUsernameFromToken(token);
            isTokenValide = !jwtTokenUtil.isTokenExpired(token) && emailFromToken.equals(email);
        } catch (Exception e) {
            System.out.println("erreur = " + e);
            isTokenValide = false;
        }
        tokenJWTHelper.setCredentialValide(isTokenValide);
        logger.info("Présentation d'un comboToken pour validation" + tokenJWTHelper);
        return tokenJWTHelper;

    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
