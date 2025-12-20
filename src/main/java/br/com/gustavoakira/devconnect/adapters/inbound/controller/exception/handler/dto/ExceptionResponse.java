package br.com.gustavoakira.devconnect.adapters.inbound.controller.exception.handler.dto;

import java.time.Instant;
import java.util.List;

public record ExceptionResponse(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path,
        String code,
        List<ErrorDetail> errorDetailList
){
    public record ErrorDetail(
            String field,
            String message
    ){

    }
}
