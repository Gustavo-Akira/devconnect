package br.com.gustavoakira.devconnect.adapters.inbound.controller.auth.dto;

import br.com.gustavoakira.devconnect.application.usecases.auth.command.TokenGrantCommand;

public record TokenRequest(String grantType, String username, String password) {

    public TokenGrantCommand toCommand(){
        return new TokenGrantCommand(
                grantType,
                username,
                password
        );
    }
}

