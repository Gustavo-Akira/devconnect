package br.com.gustavoakira.devconnect.adapters.outbound.exceptions;

public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(String message){
        super(message);
    }
}
