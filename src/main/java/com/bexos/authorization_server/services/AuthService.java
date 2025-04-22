package com.bexos.authorization_server.services;

import com.bexos.authorization_server.dto.SignupRequest;
import org.springframework.ui.Model;

public interface AuthService {
    void createUser(SignupRequest signupRequest, Model model);
}
