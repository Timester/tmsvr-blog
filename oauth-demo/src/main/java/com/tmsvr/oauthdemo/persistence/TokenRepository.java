package com.tmsvr.oauthdemo.persistence;

import java.util.Optional;

public interface TokenRepository {
    Token save(Token token);
    Optional<Token> findByUserId(long userId);
}
