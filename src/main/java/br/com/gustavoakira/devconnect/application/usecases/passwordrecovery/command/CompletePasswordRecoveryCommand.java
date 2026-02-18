package br.com.gustavoakira.devconnect.application.usecases.passwordrecovery.command;

public record CompletePasswordRecoveryCommand(String newPassword, String token) {
}
