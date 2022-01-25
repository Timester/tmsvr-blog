package com.tmsvr.oauthdemo.service;

import com.tmsvr.oauthdemo.config.OAuthConfig;
import com.tmsvr.oauthdemo.persistence.Token;
import com.tmsvr.oauthdemo.persistence.TokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorizationService {

    private final OAuthConfig oAuthConfig;
    private final TokenRepository tokenRepository;

    public String getAuthorizationUrl() {
        return oAuthConfig.authUrl() +
                "?response_type=code" +
                "&redirect_uri=" + oAuthConfig.redirectUrl() +
                "&client_id=" + oAuthConfig.clientId() +
                "&scope=" + oAuthConfig.scopes() +
                "&state=" + generateState() +
                "&access_type=offline";
    }

    public void finishAuth(String code, String state) {
        checkState(state);

        Token token = exchangeCodeForTokens(code);

        tokenRepository.save(token);

        log.info("Auth successful. Token saved to database {}", token.refreshToken().substring(token.refreshToken().length() - 4));
    }

    private Token exchangeCodeForTokens(String code) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", oAuthConfig.clientId());
        parameters.add("client_secret", oAuthConfig.clientSecret());
        parameters.add("redirect_uri", oAuthConfig.redirectUrl());
        parameters.add("code", code);

        TokenResponse response = callTokenEndpoint(parameters);

        return createToken(response, 1L);
    }

    public Token refreshToken(Token token) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("grant_type", "refresh_token");
        parameters.add("client_id", oAuthConfig.clientId());
        parameters.add("client_secret", oAuthConfig.clientSecret());
        parameters.add("refresh_token", token.refreshToken());

        TokenResponse response = callTokenEndpoint(parameters);

        return createToken(response, token.userid());
    }

    private Token createToken(TokenResponse response, long userid) {
        return new Token(response.getAccessToken(),
                Instant.now().plusSeconds(response.getExpiresIn()),
                response.getRefreshToken(),
                Instant.now().plus(30, ChronoUnit.DAYS), // this is usually documented by the auth provider
                Instant.now(),
                userid
        );
    }

    private TokenResponse callTokenEndpoint(MultiValueMap<String, String> parameters) {
        String tokenUrl = oAuthConfig.tokenUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(parameters, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForEntity(tokenUrl, request, TokenResponse.class).getBody();
    }

    private String generateState() {
        // generate a state string that you have to store until the auth flow is completed,
        // or it could be computed from some user property for example
        return "dont-do-this";
    }

    private void checkState(String state) {
        // validate that the state is the same that we have sent
        // throw exception if not
    }
}
