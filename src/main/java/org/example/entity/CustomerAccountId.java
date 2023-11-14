package org.example.entity;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CustomerAccountId implements Serializable {

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Account account;

    public CustomerAccountId() {
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerAccountId that = (CustomerAccountId) o;
        return Objects.equals(customer, that.customer) && Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, account);
    }
}
