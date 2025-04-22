package com.bexos.authorization_server.services;

import com.bexos.authorization_server.dto.SignupRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface AuthService {
    void createUser(SignupRequest signupRequest, RedirectAttributes redirectAttributes);
}
