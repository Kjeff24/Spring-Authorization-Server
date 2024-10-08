package com.bexos.authorization_server.federated;

import com.bexos.authorization_server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        System.out.println(oAuth2User);

        // Extract GitHub user info (you may need to adjust depending on the response)
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String login = (String) attributes.get("login");

        // Check if user exists
//        User user = userRepository.findByEmail(email)
//                .orElse(new User(email, login));

        // Logic to assign role based on criteria (e.g., GitHub organization, email domain)
        // You can customize this logic to decide how to assign the role
//        if (isTeacher(email)) {
//            user.setRole(Role.TEACHER);
//        } else {
//            user.setRole(Role.USER);
//        }
//
//        userRepository.save(user);

        // Map authorities for Spring Security
        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new DefaultOAuth2User(authorities, attributes, "login");
    }

    private boolean isTeacher(String email) {
        // Implement logic to determine if the user is a teacher
        // For example, check if the email belongs to a specific domain
        return email.endsWith("@teacher-domain.com");
    }
}

