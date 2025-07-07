package br.com.gustavoakira.devconnect.application.usecases.devprofile.command;

public class SaveDevProfileCommand {
    private final String name;
    private final String email;
    private final String password;
    private final String street;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String country;
    private final String githubLink;
    private final String linkedinLink;
    private final String bio;

    public SaveDevProfileCommand(String name, String email, String password,
                                 String street, String city, String state,
                                 String zipCode, String country, String githubLink, String linkedinLink, String bio) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
        this.githubLink = githubLink;
        this.linkedinLink = linkedinLink;
        this.bio = bio;
    }

    public String name() { return name; }
    public String email() { return email; }
    public String password() { return password; }
    public String street() { return street; }
    public String city() { return city; }
    public String state() { return state; }
    public String zipCode() { return zipCode; }
    public String country() { return country; }
    public String githubLink() { return githubLink; }
    public String linkedinLink() { return linkedinLink; }
    public String bio() { return bio; }
}
