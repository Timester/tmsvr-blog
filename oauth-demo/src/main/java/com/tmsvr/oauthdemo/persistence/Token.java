package com.tmsvr.oauthdemo.persistence;

import java.time.Instant;

public record Token (
        String accessToken,
        Instant accessTokenExpiresAt,
        String refreshToken,
        Instant refreshTokenExpiresAt,
        Instant createdAt,
        long userid) {
}
