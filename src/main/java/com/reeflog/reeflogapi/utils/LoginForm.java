package com.reeflog.reeflogapi.utils;

// util bean pour mapper le formulaire Login pour member


public class LoginForm {

    String email;
    String password;

    public LoginForm() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
