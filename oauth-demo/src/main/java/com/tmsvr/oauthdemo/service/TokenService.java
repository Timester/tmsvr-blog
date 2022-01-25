package com.tmsvr.oauthdemo.service;

import com.tmsvr.oauthdemo.persistence.Token;
import com.tmsvr.oauthdemo.persistence.TokenRepository;
import com.tmsvr.oauthdemo.service.exception.TokenExpiredException;
import com.tmsvr.oauthdemo.service.exception.TokenNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final AuthorizationService authorizationService;

    public Token getToken(long userId) {
        Token token = tokenRepository.findByUserId(userId).orElseThrow(TokenNotFoundException::new);

        if (token.accessTokenExpiresAt().isAfter(Instant.now().plusSeconds(5))) {
            return token;
        }

        if (token.refreshTokenExpiresAt().isBefore(Instant.now())) {
            throw new TokenExpiredException("Refresh token expired!");
        } else {
            return refreshToken(token);
        }
    }

    private Token refreshToken(Token token) {
        Token newToken = authorizationService.refreshToken(token);
        return tokenRepository.save(newToken);
    }
}
