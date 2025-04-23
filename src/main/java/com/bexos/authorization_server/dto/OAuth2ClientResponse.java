package com.bexos.authorization_server.dto;

import com.bexos.authorization_server.models.TokenSettings;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OAuth2ClientResponse {
    private String clientId;
    private String clientName;
    private Set<String> clientAuthenticationMethods;
    private Set<String> authorizationGrantTypes;
    private Set<String> redirectUris;
    private Set<String> scopes;
    private boolean requireAuthorizationConsent;
    private TokenSettings tokenSettings;
}
