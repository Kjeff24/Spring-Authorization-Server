package com.bexos.authorization_server.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenSettings {
    @Builder.Default
    private Duration accessTokenTimeToLive = Duration.ofHours(6);;
    private Duration refreshTokenTimeToLive = Duration.ofDays(1);
    private boolean reuseRefreshTokens;
}
