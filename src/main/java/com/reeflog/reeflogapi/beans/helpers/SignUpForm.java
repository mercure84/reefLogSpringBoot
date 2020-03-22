package com.reeflog.reeflogapi.beans.helpers;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

// util bean pour mapper le formulaire SignUp pour member
@Data
public class SignUpForm {

    private int idToUpdate ;

    @Size(min=3, max=15)
    private String userName;

    @Email
    private String email;

    @Size(min=5, max = 15)
    private String password;

    @NotNull
    private String repassword;


    public boolean checkPassWord (){
        return this.password.equals(this.repassword);
    }



}
