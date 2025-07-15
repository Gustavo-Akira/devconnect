package br.com.gustavoakira.devconnect.adapters.inbound.controller.auth.dto;

public record TokenResponse(String access_token, String token_type, Long expires_in) {}

