package com.reeflog.reeflogapi.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {



    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;

    }

    public void sendMail(String adresseMail, String objet, String message){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(adresseMail);
        mailMessage.setSubject(objet);
        mailMessage.setText(message);
        mailMessage.setFrom("mercure8492@gmail.com");
        javaMailSender.send(mailMessage);

    }


}
