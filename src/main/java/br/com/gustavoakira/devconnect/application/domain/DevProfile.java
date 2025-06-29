package br.com.gustavoakira.devconnect.application.domain;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.domain.value_object.Password;


public class DevProfile extends User{
    private String bio;
    private Address address;
    private String githubLink;
    private String linkedinLink;

    public DevProfile(String name, String email, String password, String bio, Address address, String githubLink, String linkedinLink, Boolean isActive) throws BusinessException {
        super(name,password,email,isActive);
        this.bio = bio;
        this.address = address;
        this.githubLink = githubLink;
        this.linkedinLink = linkedinLink;
        validate();
    }

    public DevProfile(Long id, String name, String email, String password, String bio, Address address, String githubLink, String linkedinLink, Boolean isActive) throws BusinessException {
        this(name,email,password,bio,address,githubLink,linkedinLink,isActive);
        setId(id);
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





    private void validate() throws BusinessException {
        validateGithubLink(githubLink);
        validateLinkedin(linkedinLink);
    }



    private void validateLinkedin(String linkedinLink) throws BusinessException {
        if(linkedinLink == null || !linkedinLink.startsWith("https://linkedin.com/")){
            throw new BusinessException("Informe um linkedin válido");
        }
    }

    private void validateGithubLink(String link) throws BusinessException {
        if (link == null || !link.startsWith("https://github.com/")) {
            throw new BusinessException("Informe um GitHub válido");
        }
    }
}
