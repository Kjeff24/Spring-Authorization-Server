package com.bexos.authorization_server.services.impl;

import com.bexos.authorization_server.dto.SignupRequest;
import com.bexos.authorization_server.models.User;
import com.bexos.authorization_server.repositories.UserRepository;
import com.bexos.authorization_server.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[^a-zA-Z0-9 ])" +
                    "(?=\\S+$)" +
                    ".{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public void createUser(SignupRequest signupRequest, RedirectAttributes redirectAttributes) {
        if (userRepository.existsByEmailIgnoreCase(signupRequest.getEmail())) {
            redirectAttributes.addAttribute("errorMessage", "User with this email already exists.");

        } else if (userRepository.existsByUsernameIgnoreCase(signupRequest.getUsername())) {
            redirectAttributes.addAttribute("errorMessage", "User with this username already exists.");

        } else if (!isValidPassword(signupRequest.getPassword())) {
            redirectAttributes.addAttribute(
                    "errorMessage",
                    "Password must be at least 8 characters long and include a combination of uppercase letters, lowercase letters, special characters, and numbers.");

        } else {
            userRepository.save(modelMapper.map(signupRequest, User.class));
            redirectAttributes.addAttribute(
                    "successMessage",
                    "Registration was successful, verification email has been sent to your account.");

        }
    }

    public boolean isValidPassword(final String password) {
        return pattern.matcher(password).matches();
    }
}
