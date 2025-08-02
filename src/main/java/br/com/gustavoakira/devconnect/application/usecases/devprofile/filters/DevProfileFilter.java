package br.com.gustavoakira.devconnect.application.usecases.devprofile.filters;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        final DevProfileFilter filter = (DevProfileFilter) o;
        return Objects.equals(name, filter.name) && Objects.equals(city, filter.city) && Objects.equals(stack, filter.stack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, city, stack);
    }
}
