package com.bexos.authorization_server.models;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "oauth2_clients")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OAuth2Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String clientId;
    private String clientSecret;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_authentication_methods", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "authentication_method")
    private Set<String> clientAuthenticationMethods;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "authorization_grant_types", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "grant_type")
    private Set<String> authorizationGrantTypes;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "redirect_uris", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "redirect_uri")
    private Set<String> redirectUris;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_scopes", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "scope")
    private Set<String> scopes;
    private boolean requireAuthorizationConsent;
    @Embedded
    private TokenSettings tokenSettings;
}
