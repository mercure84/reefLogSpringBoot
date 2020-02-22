package com.reeflog.reeflogapi.beans.helpers;

import lombok.Data;

@Data
public class TokenJWTHelper {

    private String token;
    private String email;
    private boolean isCredentialValide = false;


}
