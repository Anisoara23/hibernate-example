package org.example.pojo;

public class CustomerFinancialProfile {

    private String financialProfileId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String idnp;

    public CustomerFinancialProfile(String financialProfileId,
                                    String firstName,
                                    String lastName,
                                    String email,
                                    String phoneNumber,
                                    String idnp) {
        this.financialProfileId = financialProfileId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.idnp = idnp;
    }

    @Override
    public String toString() {
        return "financialProfileId='" + financialProfileId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", idnp='" + idnp + '\'';
    }
}
