package org.example.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "customer_info")
public class CustomerInfo {

    @Id
    @GenericGenerator(name = "generator", strategy = "foreign",
            parameters = @Parameter(name = "property", value = "customer"))
    @GeneratedValue(generator = "generator")
    private Integer id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code"))
    })
    @Column(nullable = false)
    private Address address;

    private String occupation;

    @Column(nullable = false)
    private String citizenship;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToOne
    private Customer customer;

    public CustomerInfo() {
    }

    public CustomerInfo(Address address, String occupation, String citizenship, Gender gender) {
        this.address = address;
        this.occupation = occupation;
        this.citizenship = citizenship;
        this.gender = gender;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
