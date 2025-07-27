package br.com.gustavoakira.devconnect.application.domain.value_object;


public class Password {
    private final String value;

    public Password(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}