package br.com.gustavoakira.devconnect.application.domain.value_object;

import br.com.gustavoakira.devconnect.application.domain.exceptions.BusinessException;

import java.util.Arrays;

public class Address {
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    private final String[] ALLOWED_COUNTRIES = new String[]{"BR","CA","USA"};

    public Address(String street, String city, String state, String country, String zipCode) throws BusinessException {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        validate();
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getZipCode() {
        return zipCode;
    }

    private void validate() throws BusinessException {
        if(Arrays.stream(ALLOWED_COUNTRIES).noneMatch(allowedCountry->allowedCountry.equals(country))){
            throw new BusinessException();
        }
    }
}
