package br.com.gustavoakira.devconnect.application.domain.exceptions;

public class BusinessException extends Exception{
    public BusinessException(String message) {
        super(message);
    }
}
