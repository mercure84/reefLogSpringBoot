package com.reeflog.reeflogapi.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class Member {

    @Id
    @GeneratedValue
    private int id;
    private String lastName;
    private String firstName;
    private String userName;
    private String email;
    private String password;
    private Date signupDate = new Date();
    private String role = "USER";
    private MemberStatus memberStatus;

    public enum MemberStatus {VALIDATION_EMAIL, BANNED, EMAIL_CONFIRMED, BLOCKED }

}
