package com.tmsvr.oauthdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("awesomeapp.oauth")
public record OAuthConfig(
        String clientId,
        String clientSecret,
        String authUrl,
        String tokenUrl,
        String redirectUrl,
        String scopes
) { }
