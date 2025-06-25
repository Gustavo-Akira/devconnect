package br.com.gustavoakira.devconnect.application.domain;

import br.com.gustavoakira.devconnect.application.domain.value_object.Address;

import java.util.List;

public class DevProfile {
    private Long id;
    private String name;
    private String email;
    private String bio;
    private Address address;
    private List<Project> projects;

    public DevProfile(Long id, String name, String email, String bio, Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.address = address;
    }

    public DevProfile(String name, String email, String bio, Address address) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.address = address;
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

    public void addProject(Project project){

    }
}
