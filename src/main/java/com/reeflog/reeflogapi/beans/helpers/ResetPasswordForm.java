package com.reeflog.reeflogapi.beans.helpers;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ResetPasswordForm {

    private int idToUpdate ;

    @Size(min=5, max = 15)
    private String password;

    @NotNull
    private String repassword;

    private String token;


    public boolean checkPassWord (){
        return this.password.equals(this.repassword);
    }

}
