package br.com.gustavoakira.devconnect.application.usecases.devprofile.filters;

import java.util.List;

public class DevProfileFilter {

    private final String name;
    private final String city;
    private final String tech;
    private final List<String> stack;

    public DevProfileFilter(String name, String city, String tech, List<String> stack) {
        this.name = name;
        this.city = city;
        this.tech = tech;
        this.stack = stack;
    }

    public String name() {
        return name;
    }

    public String city() {
        return city;
    }

    public String tech() {
        return tech;
    }

}
