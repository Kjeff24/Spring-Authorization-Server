package com.bexos.authorization_server.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.Set;

@Getter
@Setter
public class OAuth2ClientRequest {
    private String clientId;
    private String clientSecret;
    private String clientName;
    private Set<String> authenticationMethods;
    private Set<String> authorizationGrantTypes;
    private Set<String> redirectUris;
    private Set<String> scopes;
    private boolean requireProofKey;
    private Duration accessTokenTimeToLive;
    private Duration refreshTokenTimeToLive;
}
