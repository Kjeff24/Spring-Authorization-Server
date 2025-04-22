package com.bexos.authorization_server.controller;

import com.bexos.authorization_server.dto.SignupRequest;
import com.bexos.authorization_server.services.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        HttpSession session, Model model) {
        if (error != null) {
            AuthenticationException ex = (AuthenticationException)
                    session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");

            String errorMessage = switch (ex.getClass().getSimpleName()) {
                case "BadCredentialsException" -> "Invalid username or password";
                case "DisabledException" -> "Your account is disabled";
                case "LockedException" -> "Your account is locked";
                case "AccountExpiredException" -> "Your account has expired";
                default -> "Login failed. Please try again.";
            };

            model.addAttribute("errorMessage", errorMessage);
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/signup")
    public String formRegister(@ModelAttribute("user") SignupRequest signupRequest, RedirectAttributes redirectAttributes) {
        authService.createUser(signupRequest, redirectAttributes);
        return "signup";
    }
}
