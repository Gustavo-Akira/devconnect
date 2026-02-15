package br.com.gustavoakira.devconnect.adapters.inbound.controller.passwordrecovery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestPasswordRecoveryRequest(@NotBlank @NotNull String email) {
}
