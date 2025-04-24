package com.bexos.authorization_server.services.impl;

import com.bexos.authorization_server.dto.OAuth2ClientRequest;
import com.bexos.authorization_server.dto.OAuth2ClientResponse;
import com.bexos.authorization_server.exception.NotFoundException;
import com.bexos.authorization_server.models.OAuth2Client;
import com.bexos.authorization_server.repositories.OAuth2ClientRepository;
import com.bexos.authorization_server.services.OAuth2ClientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OAuth2ClientServiceImpl implements RegisteredClientRepository, OAuth2ClientService {
    private final OAuth2ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        return clientRepository.findById(UUID.fromString(id))
                .map(this::toRegisteredClient)
                .orElseThrow(() -> new NotFoundException("Client with id " + id + " not found. Please check your client id and try again."));
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return clientRepository.findByClientId(clientId)
                .map(this::toRegisteredClient)
                .orElseThrow(() -> new NotFoundException("Client with id " + clientId + " not found. Please check your client id and try again."));
    }

    public OAuth2ClientResponse save(OAuth2ClientRequest clientRequest) {

        OAuth2Client client = OAuth2Client.builder()
                .clientId(clientRequest.getClientId())
                .clientSecret(passwordEncoder.encode(clientRequest.getClientSecret()))
                .clientAuthenticationMethods(clientRequest.getClientAuthenticationMethods())
                .authorizationGrantTypes(clientRequest.getAuthorizationGrantTypes())
                .redirectUris(clientRequest.getRedirectUris())
                .scopes(clientRequest.getScopes())
                .tokenSettings(com.bexos.authorization_server.models.TokenSettings.builder()
                        .accessTokenTimeToLive(clientRequest.getAccessTokenTimeToLive())
                        .refreshTokenTimeToLive(clientRequest.getRefreshTokenTimeToLive())
                        .reuseRefreshTokens(clientRequest.isReuseRefreshTokens())
                        .build())
                .requireAuthorizationConsent(clientRequest.isRequireProofKey())
                .build();
        return modelMapper.map(clientRepository.save(client), OAuth2ClientResponse.class);
    }

    public List<OAuth2ClientResponse> findAllClients() {
        return clientRepository.findAll().stream().map((element) -> modelMapper.map(element, OAuth2ClientResponse.class))
                .toList();
    }

    public OAuth2ClientResponse findByOAuth2ClientId(String clientId) {
        return modelMapper.map(clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new NotFoundException("Client with id " + clientId + " not found. Please check your client id and try again.")), OAuth2ClientResponse.class);
    }

    private RegisteredClient toRegisteredClient(OAuth2Client client) {
        return RegisteredClient.withId(client.getClientId())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientIdIssuedAt(new Date().toInstant())
                .clientAuthenticationMethods(methods ->
                        client.getClientAuthenticationMethods().forEach(method ->
                                methods.add(new ClientAuthenticationMethod(method))))
                .authorizationGrantTypes(grants ->
                        client.getAuthorizationGrantTypes().forEach(grant ->
                                grants.add(new AuthorizationGrantType(grant))))
                .redirectUris(uri -> uri.addAll(client.getRedirectUris()))
                .scopes(scope -> scope.addAll(client.getScopes()))
                .clientSettings(ClientSettings.builder()
                        .requireProofKey(client.isRequireAuthorizationConsent())
                        .build())
                .tokenSettings(TokenSettings.builder()
                        .refreshTokenTimeToLive(client.getTokenSettings().getRefreshTokenTimeToLive())
                        .accessTokenTimeToLive(client.getTokenSettings().getAccessTokenTimeToLive())
                        .reuseRefreshTokens(client.getTokenSettings().isReuseRefreshTokens())
                        .build())
                .build();
    }
}
