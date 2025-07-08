package br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.dto;

import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.SaveDevProfileCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SaveDevProfileRequest {
    @NotNull
    @NotEmpty
    private final String name;
    @NotNull
    @Email
    private final String email;
    @NotNull
    @NotEmpty
    private final String password;
    @NotNull
    @NotEmpty
    private final String street;
    @NotNull
    @NotEmpty
    private final String city;
    @NotNull
    @NotEmpty
    private final String state;
    @NotNull
    @NotEmpty
    private final String zipCode;
    @NotNull
    @Size(min = 2)
    @Size(max = 2)
    private final String country;
    @NotNull
    @NotEmpty
    private final String githubLink;
    @NotNull
    @NotEmpty
    private final String linkedinLink;
    @NotNull
    @NotEmpty
    private final String bio;

    public SaveDevProfileCommand toCommand(){
        return new SaveDevProfileCommand(name, email, password, street, city, state, zipCode, country, githubLink, linkedinLink, bio);
    }
}
