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
    private Long userId;
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

    public DevProfileEntity(Long id,Long userId, String name,String bio, AddressEntity address, String githubLink, String linkedinLink,List<String> techStack) {
        super(id,name,null,null,null);
        this.bio = bio;
        this.userId = userId;
        this.address = address;
        this.githubLink = githubLink;
        this.linkedinLink = linkedinLink;
        this.techStack = techStack;
    }

    public DevProfileEntity(Long userId,String name,String bio, AddressEntity address, String githubLink, String linkedinLink, List<String> techStack) {
        super(null,name,null,null,null);
        this.bio = bio;
        this.userId = userId;
        this.address = address;
        this.githubLink = githubLink;
        this.linkedinLink = linkedinLink;
        this.techStack = techStack;
    }
}
