package br.com.gustavoakira.devconnect.application.domain;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;

import java.time.Instant;

public class PasswordRecovery {
    private final Long id;
    private final String token;
    private final Long userId;
    private final Instant expiresAt;
    private Instant usedAt;

    public PasswordRecovery(Long id, String token, Long userId, Instant expiresAt) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token cannot be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("UserId cannot be null");
        }
        if (expiresAt == null) {
            throw new IllegalArgumentException("ExpiresAt cannot be null");
        }
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.expiresAt = expiresAt;
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Long getUserId() {
        return userId;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public Instant getUsedAt() {
        return usedAt;
    }

    public boolean isExpired(Instant now) {
        return now.isAfter(expiresAt);
    }

    public boolean isUsed() {
        return usedAt != null;
    }

    public boolean isValid(Instant now) {
        return !isUsed() && !isExpired(now);
    }

    public void markAsUsed(Instant now) throws BusinessException {
        if (isUsed()) {
            throw new BusinessException("Token already used");
        }

        if (isExpired(now)) {
            throw new BusinessException("Token expired");
        }

        this.usedAt = now;
    }
}