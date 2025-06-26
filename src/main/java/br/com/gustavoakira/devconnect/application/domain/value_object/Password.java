package br.com.gustavoakira.devconnect.application.domain.value_object;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;

public class Password {
    private final String value;

    public Password(String value) throws BusinessException {
        validate(value);
        this.value = value;
    }

    private void validate(String value) throws BusinessException {
        if (value.length() < 8) throw new BusinessException("Senha muito curta");
        if (!value.matches(".*[A-Z].*")) throw new BusinessException("Deve conter uma letra maiúscula");
        if (!value.matches(".*[a-z].*")) throw new BusinessException("Deve conter uma letra minúscula");
        if (!value.matches(".*\\d.*")) throw new BusinessException("Deve conter um número");
        if (!value.matches(".*[!@#$%^&*()].*")) throw new BusinessException("Deve conter um caractere especial");
    }

    public String getValue() {
        return value;
    }
}