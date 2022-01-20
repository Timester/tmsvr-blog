package com.tmsvr.oauthdemo.service;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TokenResponse {
    @JsonProperty("access_token")
    String accessToken;

    @JsonProperty("token_type")
    String tokenType;

    @JsonProperty("scope")
    String scope;

    @JsonProperty("expires_in")
    int expiresIn;

    @JsonProperty("refresh_token")
    String refreshToken;

    @JsonProperty("id_token")
    String idToken;
}
