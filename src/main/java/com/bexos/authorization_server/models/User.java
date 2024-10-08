package com.bexos.authorization_server.models;

import com.bexos.authorization_server.enums.Permission;
import com.bexos.authorization_server.enums.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
@Document(value = "Users")
public class User implements UserDetails {
    @Id
    private String id;
    private String fullName;
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;
    private String password;
    private String pictureUrl;
    private boolean isEnabled;
    private Role role;
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> permissionAuthorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList();

        return Stream.concat(role.getAuthorities().stream(), permissionAuthorities.stream())
                .collect(Collectors.toSet());
    }
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public static User fromOAuth2GithubUser(OAuth2User user){
        return User.builder()
                .email(user.getAttribute("email"))
                .fullName(user.getAttribute("name"))
                .username(user.getAttribute("login"))
                .pictureUrl(user.getAttribute("avatar_url"))
//                .roles(roles)
                .isEnabled(true)
                .build();
    }

    public static User fromOauth2GoogleUser(OAuth2User user){
        return User.builder()
                .email(user.getAttributes().get("email").toString())
                .fullName(user.getAttributes().get("name").toString())
                .username(user.getAttributes().get("given_name").toString())
                .pictureUrl(user.getAttributes().get("picture").toString())
//                .roles(roles)
                .isEnabled(true)
                .build();
    }
}
