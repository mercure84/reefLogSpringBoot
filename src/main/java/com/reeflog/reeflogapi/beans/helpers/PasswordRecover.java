package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.Member;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.Random;

@Data
@Entity
public class PasswordRecover {

    @Id @GeneratedValue
    private int Id;

    private Date initialDate;

    @ManyToOne
    private Member member;
    private String urlToken;

    public void setUrlToken() {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 64;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        this.urlToken = generatedString;
    }



}
