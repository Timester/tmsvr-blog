package com.tmsvr.oauthdemo.persistence;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryTokenRepository implements TokenRepository {

    private final Map<Long, Token> tokenStore;

    public InMemoryTokenRepository() {
        this.tokenStore = new HashMap<>();
    }

    @Override
    public Token save(Token token) {
        return this.tokenStore.put(token.userid(), token);
    }

    @Override
    public Optional<Token> findByUserId(long userId) {
        return Optional.ofNullable(tokenStore.get(userId));
    }
}
