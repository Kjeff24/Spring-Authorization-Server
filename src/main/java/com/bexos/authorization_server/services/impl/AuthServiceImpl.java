package com.bexos.authorization_server.services.impl;

import com.bexos.authorization_server.dto.SignupRequest;
import com.bexos.authorization_server.models.User;
import com.bexos.authorization_server.repositories.UserRepository;
import com.bexos.authorization_server.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[^a-zA-Z0-9 ])" +
                    "(?=\\S+$)" +
                    ".{8,}$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public void createUser(SignupRequest signupRequest, Model model) {

        if (!signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            model.addAttribute(
                    "errorMessage",
                    "Passwords do not match. Please try again.");
        } else if (!isValidPassword(signupRequest.getPassword())) {
            model.addAttribute(
                    "errorMessage",
                    "Password must be at least 8 characters long and include a combination of uppercase letters, lowercase letters, special characters, and numbers.");

        } else if (userRepository.existsByEmailIgnoreCase(signupRequest.getEmail())) {
            model.addAttribute("errorMessage", "User with this email already exists.");

        } else if (userRepository.existsByUsernameIgnoreCase(signupRequest.getUsername())) {
            model.addAttribute("errorMessage", "User with this username already exists.");

        } else {
            User user = modelMapper.map(signupRequest, User.class);
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            userRepository.save(user);
            model.addAttribute(
                    "successMessage",
                    "Registration was successful.");

        }
    }

    public boolean isValidPassword(final String password) {
        return pattern.matcher(password).matches();
    }
}
