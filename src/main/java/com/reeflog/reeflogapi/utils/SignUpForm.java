package com.reeflog.reeflogapi.utils;

// util bean pour mapper le formulaire SignUp pour member

public class SignUpForm {

    private String lastName;
    private String firstName;
    private String nickName;
    private String email;
    private String password;
    private String repassword;

    public SignUpForm(String lastName, String firstName, String nickName, String email, String password, String repassword) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.repassword = repassword;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
