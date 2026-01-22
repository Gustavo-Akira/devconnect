package br.com.gustavoakira.devconnect.application.usecases.devprofile.command;

import java.util.List;

public record UpdateDevProfileCommand(Long id, String name, String street, String city,
                                      String state, String zipCode, String country, String githubLink,
                                      String linkedinLink, String bio, List<String> stack) {
}
