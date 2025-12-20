package br.com.gustavoakira.devconnect.adapters.inbound.controller.exception.handler;

import jakarta.validation.constraints.NotBlank;

public record TestDto(@NotBlank String name) {}
