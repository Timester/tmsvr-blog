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
        String tokenUrl = oAuthConfig.tokenUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", oAuthConfig.clientId());
        map.add("client_secret", oAuthConfig.clientSecret());
        map.add("redirect_uri", oAuthConfig.redirectUrl());
        map.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();
        TokenResponse response = restTemplate.postForEntity(tokenUrl, request, TokenResponse.class).getBody();

        return new Token(response.getAccessToken(),
                Instant.now().plusSeconds(response.getExpiresIn()),
                response.getRefreshToken(),
                Instant.now().plus(30, ChronoUnit.DAYS),
                Instant.now(),
                1L
        );
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
