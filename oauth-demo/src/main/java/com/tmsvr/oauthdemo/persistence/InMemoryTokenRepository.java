package com.tmsvr.oauthdemo.persistence;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

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
}
