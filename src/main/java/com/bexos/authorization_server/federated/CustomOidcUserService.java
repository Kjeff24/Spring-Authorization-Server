package com.bexos.authorization_server.federated;

import com.bexos.authorization_server.enums.Role;
import com.bexos.authorization_server.models.User;
import com.bexos.authorization_server.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOidcUserService extends OidcUserService {

    private final UserRepository userRepository;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        User user = processOidcUser(oidcUser, provider);

        return new DefaultOidcUser(
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name())),
                oidcUser.getIdToken(),
                oidcUser.getUserInfo(),
                "sub"
        );
    }

    private User processOidcUser(OidcUser oidcUser, String provider) {
        String email = oidcUser.getEmail();
        return userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .email(oidcUser.getEmail())
                            .fullName(oidcUser.getFullName())
                            .username(oidcUser.getGivenName())
                            .pictureUrl(oidcUser.getPicture())
                            .role(Role.USER)
                            .isEnabled(true)
                            .provider(provider)
                            .build();
                    return userRepository.save(newUser);
                });
    }
}
