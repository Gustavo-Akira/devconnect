package br.com.gustavoakira.devconnect.application.domain;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Password;

public class User {
    private Long id;
    @Deprecated
    private String name;
    private Password password;
    private String email;
    private Boolean isActive;



    public User(String name, String password,String email, Boolean isActive) throws BusinessException {
        this.name = name;
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

    @Deprecated
    public void rename(String name) {
        this.name = name;
    }

    public User(Long id, String name, String password, String email, Boolean isActive) throws BusinessException {
        this(name,password,email,isActive);
        this.id = id;
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
    @Deprecated
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }



}
