package com.bexos.authorization_server.repositories;

import com.bexos.authorization_server.models.OAuth2Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OAuth2ClientRepository extends JpaRepository<OAuth2Client, UUID> {
}
