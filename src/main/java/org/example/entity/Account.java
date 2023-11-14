package org.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account extends FinancialProfile {

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountType type;

    @OneToMany(mappedBy = "id.account")
    private Set<CustomerAccount> customers = new HashSet<>();

    public Account() {
    }

    public Account(AccountType type) {
        this.type = type;
    }

    public Set<CustomerAccount> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<CustomerAccount> customers) {
        this.customers = customers;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
}
