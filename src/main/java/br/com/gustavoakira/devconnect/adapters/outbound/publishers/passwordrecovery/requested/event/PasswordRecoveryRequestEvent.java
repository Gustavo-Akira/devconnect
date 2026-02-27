package br.com.gustavoakira.devconnect.adapters.outbound.publishers.passwordrecovery.requested.event;

import java.time.Instant;

public record PasswordRecoveryRequestEvent(
        Long userId,
        String link,
        String email,
        Instant expiresIn
){

}
