package com.reeflog.reeflogapi.beans.helpers;

import com.reeflog.reeflogapi.beans.Member;
import lombok.Data;

@Data
public class GoogleForm {
    String email;
    String tokenId;
    Member member;
    String jwtToken;

}
