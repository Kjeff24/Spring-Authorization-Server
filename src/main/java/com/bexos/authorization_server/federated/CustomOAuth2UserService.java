package com.bexos.authorization_server.federated;

import com.bexos.authorization_server.enums.Role;
import com.bexos.authorization_server.models.User;
import com.bexos.authorization_server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        log.info("Loading user {}", oAuth2User);

        if (!"github".equalsIgnoreCase(provider)) {
            throw new OAuth2AuthenticationException("Unsupported OAuth2 provider: " + provider);
        }

        User user = processGitHubUser(oAuth2User, provider);

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
                oAuth2User.getAttributes(),
                "id"
        );
    }

    private User processGitHubUser(OAuth2User oAuth2User, String provider) {
        String login = (String) oAuth2User.getAttribute("login");
        String email = (String) oAuth2User.getAttribute("email");
        String userEmail = email != null ? email : login + "@github.com";

        return userRepository.findByEmailIgnoreCase(userEmail)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(oAuth2User.getAttribute("email"))
                            .fullName(oAuth2User.getAttribute("name"))
                            .username(oAuth2User.getAttribute("login"))
                            .pictureUrl(oAuth2User.getAttribute("avatar_url"))
                            .role(Role.USER)
                            .isEnabled(true)
                            .provider(provider)
                            .build();
                    return userRepository.save(newUser);
                });
    }
}
