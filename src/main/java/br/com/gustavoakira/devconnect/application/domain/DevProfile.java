package br.com.gustavoakira.devconnect.application.domain;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.domain.value_object.Password;

import java.util.ArrayList;
import java.util.List;

public class DevProfile {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private Password password;
    private Address address;
    private String githubLink;
    private String linkedinLink;

    public DevProfile(String name, String email, String password, String bio, Address address, String githubLink, String linkedinLink) throws BusinessException {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.address = address;
        this.githubLink = githubLink;
        this.linkedinLink = linkedinLink;
        this.password = new Password(password);
        validate();
    }

    public DevProfile(Long id, String name, String email, String password, String bio, Address address, String githubLink, String linkedinLink) throws BusinessException {
        this(name,email,password,bio,address,githubLink,linkedinLink);
        this.id = id;
    }



    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public Address getAddress() {
        return address;
    }

    public String getGithubLink() {
        return githubLink;
    }

    public String getLinkedinLink() {
        return linkedinLink;
    }

    public void moveToNewAddress(Address address){
        this.address = address;
    }

    public void rename(String name) throws BusinessException {
        validateName(name);
        this.name = name;
    }

    public void changePassword(String password) throws BusinessException {
        this.password = new Password(password);
    }

    private void validate() throws BusinessException {
        validateName(name);
        validateGithubLink(githubLink);
        validateLinkedin(linkedinLink);
    }

    private void validateName(String name) throws BusinessException {
        if(name.split(" ").length < 2){
            throw new BusinessException("The name cannot be with only one word");
        }
    }

    private void validateLinkedin(String linkedinLink) throws BusinessException {
        if(linkedinLink == null || !linkedinLink.startsWith("https://linkedin.com/")){
            throw new BusinessException("Informe um Linkedin válido");
        }
    }

    private void validateGithubLink(String link) throws BusinessException {
        if (link == null || !link.startsWith("https://github.com/")) {
            throw new BusinessException("Informe um GitHub válido");
        }
    }
}
