package br.com.gustavoakira.devconnect.application.usecases.devprofile.filters;

public class DevProfileFilter {

    private final String name;
    private final String city;
    private final String tech;

    public DevProfileFilter(String name, String city, String tech) {
        this.name = name;
        this.city = city;
        this.tech = tech;
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
