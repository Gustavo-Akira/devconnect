package br.com.gustavoakira.devconnect.application.domain;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;
import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import br.com.gustavoakira.devconnect.application.domain.value_object.Password;

import java.util.List;

/**
 * @deprecated DevProfile currently extends User only for migration purposes.
 * This inheritance will be removed once all dependencies are migrated.
 */
public class DevProfile extends User{
    private String bio;
    private Address address;
    private String githubLink;
    private String linkedinLink;
    private List<String> stack;
    private Long userId;

    public DevProfile(Long userId,String name, String email, String password, String bio, Address address, String githubLink, String linkedinLink,List<String> stack, Boolean isActive) throws BusinessException {
        super(name,password,email,isActive);
        this.bio = bio;
        this.address = address;
        this.githubLink = githubLink;
        this.linkedinLink = linkedinLink;
        this.stack = stack;
        this.userId = userId;
        validate();
    }

    public DevProfile(Long id, Long userId,String name, String email, String password, String bio, Address address, String githubLink, String linkedinLink, List<String> stack, Boolean isActive) throws BusinessException {
        this(userId,name,email,password,bio,address,githubLink,linkedinLink,stack,isActive);
        super.setId(id);
    }

    @Deprecated
    public String getEmail() {
        return super.getEmail();
    }

    @Deprecated
    public Password getPassword() {
        return super.getPassword();
    }

    @Deprecated
    public String getName(){
        return super.getName();
    }

    @Deprecated
    public Boolean isActive(){
        return super.isActive();
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
}
