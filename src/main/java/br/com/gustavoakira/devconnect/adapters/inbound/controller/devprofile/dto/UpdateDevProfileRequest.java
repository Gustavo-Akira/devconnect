package br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.dto;

import br.com.gustavoakira.devconnect.application.usecases.devprofile.command.UpdateDevProfileCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UpdateDevProfileRequest {
    @NotNull
    private final Long id;
    @NotNull
    @NotEmpty
    private final String name;
    @NotNull
    @Email
    private final String email;
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
    @NotEmpty
    @NotNull
    private final List<String> stack;

    public UpdateDevProfileCommand toCommand(){
        return new UpdateDevProfileCommand(id, name, email, street, city, state, zipCode, country, githubLink, linkedinLink, bio,stack);
    }
}
