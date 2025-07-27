package br.com.gustavoakira.devconnect.application.usecases.auth.command;

public record TokenGrantCommand(String grantType, String username, String password) {}

