package org.example.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "postalCode", column = @Column(name = "postal_code"))
    )
    private Address address;

    @ManyToOne(cascade = CascadeType.ALL)
    private Bank bank;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
    private Set<FinancialProfile> financialProfiles = new HashSet<>();

    public Branch() {
    }

    public Branch(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void setFinancialProfiles(Set<FinancialProfile> financialProfiles) {
        this.financialProfiles = financialProfiles;
    }

    public Set<FinancialProfile> getFinancialProfiles() {
        return financialProfiles;
    }
}
