package com.reeflog.reeflogapi.beans.helpers;

import lombok.Data;

// util bean pour mapper le formulaire SignUp pour member
@Data
public class SignUpForm {

    private String lastName;
    private String firstName;
    private String userName;
    private String email;
    private String password;
    private String repassword;

}
