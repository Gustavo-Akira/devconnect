package br.com.gustavoakira.devconnect.application.usecases.auth.response;

public record TokenGrantResponse(String token, Long expiresIn) {
}
