package br.com.gustavoakira.devconnect.application.usecases.devprofile.filters;

import java.util.List;

public class DevProfileFilter {

    private final String name;
    private final String city;
    private final List<String> stack;

    public DevProfileFilter(String name, String city, List<String> stack) {
        this.name = name;
        this.city = city;
        this.stack = stack;
    }

    public String name() {
        return name;
    }

    public String city() {
        return city;
    }

    public List<String> stack() {
        return stack;
    }
}
