package org.example.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDHexGenerator")
    @Column(name = "account_number")
    private String accountNumber;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountType type;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Branch branch;

    @OneToMany(mappedBy = "id.account")
    private Set<CustomerAccount> customers = new HashSet<>();

    private BigDecimal balance;

    public Account() {
    }

    public Account(AccountType type, BigDecimal balance) {
        this.type = type;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Set<CustomerAccount> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<CustomerAccount> customers) {
        this.customers = customers;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
