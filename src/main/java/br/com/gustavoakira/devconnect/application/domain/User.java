package br.com.gustavoakira.devconnect.application.domain;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Password;

public class User {
    private Long id;
    private Password password;
    private String email;
    private Boolean isActive;



    public User(String password,String email, Boolean isActive) throws BusinessException {
        this.password = new Password(password);
        this.email = email;
        this.isActive = isActive == null || isActive;
        validate();
    }

    public User(Long id, String password,String email, Boolean isActive) throws BusinessException {
        this.id = id;
        this.password = new Password(password);
        this.email = email;
        this.isActive = isActive == null || isActive;
        validate();
    }


    public Boolean isActive() {
        return isActive;
    }

    public void disable(){
        this.isActive = false;
    }

    private void validate() throws BusinessException {
        if (!this.email.contains("@")){
            throw new BusinessException("Should contain an @ in emaill");
        }
    }

    public void changePassword(String password) throws BusinessException {
        this.password = new Password(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Password getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }



}
