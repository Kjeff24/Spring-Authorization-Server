package com.bexos.authorization_server.services;

import com.bexos.authorization_server.dto.OAuth2ClientRequest;
import com.bexos.authorization_server.dto.OAuth2ClientResponse;

import java.util.List;

public interface OAuth2ClientService {
    OAuth2ClientResponse save(OAuth2ClientRequest clientRequest);

    List<OAuth2ClientResponse> findAllClients();

    OAuth2ClientResponse findByOAuth2ClientId(String clientId);
}
