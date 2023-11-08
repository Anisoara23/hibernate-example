package org.example.entity;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

    private String country;

    private String city;

    private String street;

    private String postalCode;

    public Address() {
    }

    public Address(String country, String city, String street, String postalCode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
    }
}
