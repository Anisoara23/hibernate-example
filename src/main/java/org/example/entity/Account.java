package org.example.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Account {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDHexGenerator")
    private String accountNumber;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AccountType type;

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
}
