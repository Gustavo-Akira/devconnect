package br.com.gustavoakira.devconnect.adapters.inbound.controller.auth.dto;

public record TokenRequest(String grant_type, String username, String password) {}

