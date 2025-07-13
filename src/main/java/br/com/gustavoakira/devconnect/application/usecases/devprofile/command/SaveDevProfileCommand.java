package br.com.gustavoakira.devconnect.application.usecases.devprofile.command;

import java.util.List;

public record SaveDevProfileCommand(String name, String email, String password, String street, String city,
                                    String state, String zipCode, String country, String githubLink,
                                    String linkedinLink, String bio, List<String> stack) {
}
