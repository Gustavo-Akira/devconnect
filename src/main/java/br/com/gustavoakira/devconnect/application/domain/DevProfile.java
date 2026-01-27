package br.com.gustavoakira.devconnect.application.domain;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.domain.value_object.Password;

import java.util.List;

public class DevProfile{
    private Long id;
    private String name;
    private String bio;
    private Address address;
    private String githubLink;
    private String linkedinLink;
    private List<String> stack;
    private Long userId;

    public DevProfile(Long userId,String name, String bio, Address address, String githubLink, String linkedinLink,List<String> stack) throws BusinessException {
        this.name = name;
        this.bio = bio;
        this.address = address;
        this.githubLink = githubLink;
        this.linkedinLink = linkedinLink;
        this.stack = stack;
        this.userId = userId;
        validate();
    }

    public DevProfile(Long id, Long userId,String name, String bio, Address address, String githubLink, String linkedinLink, List<String> stack) throws BusinessException {
        this(userId,name,bio,address,githubLink,linkedinLink,stack);
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void rename(String name) throws BusinessException {
        validateName(name);
        this.name = name;
    }


    private void validateName(String name) throws BusinessException {
        if(name.split("\\s+").length < 2){
            throw new BusinessException("The name cannot be with only one word");
        }
    }


    public String getName(){
        return this.name;
    }

    public Long getUserId() {
        return userId;
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

    public List<String> getStack() {
        return stack;
    }

    public void moveToNewAddress(Address address){
        this.address = address;
    }





    private void validate() throws BusinessException {
        validateName(name);
        validateGithubLink(githubLink);
        validateLinkedin(linkedinLink);
    }



    private void validateLinkedin(String linkedinLink) throws BusinessException {
        if(linkedinLink == null || (!linkedinLink.startsWith("https://www.linkedin.com/in/") && !linkedinLink.startsWith("https://linkedin.com/in/"))){
            throw new BusinessException("Informe um linkedin válido");
        }
    }

    private void validateGithubLink(String link) throws BusinessException {
        if (link == null || !link.startsWith("https://github.com/")) {
            throw new BusinessException("Informe um GitHub válido");
        }
    }

    public void updateBio(String bio) {
        this.bio = bio;
    }


    public void updateGithubLink(String link) throws BusinessException {
        validateGithubLink(link);
        this.githubLink = link;
    }

    public void updateLinkedinLink(String link) throws BusinessException {
        validateLinkedin(link);
        this.linkedinLink = link;
    }

    public void updateStack(List<String> stack) {
        this.stack = stack;
    }
}
