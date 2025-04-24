package com.bexos.authorization_server.controller;

import com.bexos.authorization_server.dto.OAuth2ClientRequest;
import com.bexos.authorization_server.dto.OAuth2ClientResponse;
import com.bexos.authorization_server.services.OAuth2ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/oauth2-client")
@RequiredArgsConstructor
public class OAuth2ClientController {
    private final OAuth2ClientService oauth2ClientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OAuth2ClientResponse createClient(@RequestBody OAuth2ClientRequest clientRequest) {
        return oauth2ClientService.save(clientRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OAuth2ClientResponse> getAllClients() {
        return oauth2ClientService.findAllClients();
    }

    @GetMapping("/{client-id}")
    @ResponseStatus(HttpStatus.OK)
    public OAuth2ClientResponse getClientById(@RequestParam("client_id") String clientId) {
        return oauth2ClientService.findByOAuth2ClientId(clientId);
    }

}
