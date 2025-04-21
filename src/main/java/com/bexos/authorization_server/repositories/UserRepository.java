package com.bexos.authorization_server.repositories;

import com.bexos.authorization_server.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
   Optional<User> findByEmailIgnoreCase(String userEmail);
   Optional<User> findByEmailIgnoreCaseOrUsernameIgnoreCase(String email, String username);
}
