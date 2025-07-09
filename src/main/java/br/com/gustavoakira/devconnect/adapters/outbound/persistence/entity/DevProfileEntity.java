package br.com.gustavoakira.devconnect.adapters.outbound.persistence.entity;

import br.com.gustavoakira.devconnect.application.domain.value_object.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class DevProfileEntity extends UserSuperEntity {

    private String bio;
    @Embedded
    private AddressEntity address;
    private String githubLink;
    private String linkedinLink;

    @ElementCollection
    @CollectionTable(name = "dev_profile_techs", joinColumns = @JoinColumn(name = "dev_profile_id"))
    @Column(name = "tech")
    private List<String> techStack = new ArrayList<>();

    public DevProfileEntity(){
        super();
    }

    public DevProfileEntity(Long id,String name,String email,String password,String bio, AddressEntity address, String githubLink, String linkedinLink, Boolean isActive) {
        super(id,name,password,email,isActive);
        this.bio = bio;
        this.address = address;
        this.githubLink = githubLink;
        this.linkedinLink = linkedinLink;

    }

    public DevProfileEntity(String name,String email,String password,String bio, AddressEntity address, String githubLink, String linkedinLink, Boolean isActive) {
        super(null,name,password,email,isActive);
        this.bio = bio;
        this.address = address;
        this.githubLink = githubLink;
        this.linkedinLink = linkedinLink;

    }
}
