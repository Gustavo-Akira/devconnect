package br.com.gustavoakira.devconnect.adapters.inbound.controller.devprofile.dto;

import br.com.gustavoakira.devconnect.application.domain.DevProfile;
import br.com.gustavoakira.devconnect.application.domain.User;
import lombok.Data;

import java.util.List;

@Data
public class DevProfileResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final String bio;
    private final AddressResponse address;
    private final String githubLink;
    private final String linkedinLink;
    private final List<String> stack;
    private final Boolean isActive;


    @Data
    static class AddressResponse{
        private final String street;
        private final String city;
        private final String state;
        private final String zipCode;
        private final String country;
    }

    public static DevProfileResponse fromDomain(DevProfile profile, User user){
        return new DevProfileResponse(
                profile.getId(),
                profile.getName(),
                // Transitional fallback while User and DevProfile are still coupled
                user.getEmail() != null? user.getEmail() : profile.getEmail(),
                profile.getBio(),
                new AddressResponse(
                        profile.getAddress().getStreet(),
                        profile.getAddress().getCity(),
                        profile.getAddress().getState(),
                        profile.getAddress().getZipCode(),
                        profile.getAddress().getCountry()
                ),
                profile.getGithubLink(),
                profile.getLinkedinLink(),
                profile.getStack(),
                // Transitional fallback while User and DevProfile are still coupled
                user.isActive() != null? user.isActive() : profile.isActive()
        );
    }
}
