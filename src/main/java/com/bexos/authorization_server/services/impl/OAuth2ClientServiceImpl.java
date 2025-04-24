package com.bexos.authorization_server.services.impl;

import com.bexos.authorization_server.services.OAuth2ClientService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

public class OAuth2ClientServiceImpl implements RegisteredClientRepository, OAuth2ClientService {
    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return null;
    }
}
