package org.example.entity;

import org.hibernate.annotations.Check;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"phone_number", "email"})
})
@Check(constraints = "length(IDNP) = 13")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(
            name = "first_name",
            nullable = false
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false
    )
    private String lastName;

    @Column(name = "birth_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Column(name = "IDNP", nullable = false)
    private String idnp;

    @Column(
            name = "phone_number",
            nullable = false
    )
    private String phoneNumber;

    @Column(nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "customer")
    private CustomerInfo info;

    @OneToMany(mappedBy = "id.customer")
    private Set<CustomerAccount> accounts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "customer_loan",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "loan_id")
    )
    private Set<Loan> loans = new HashSet<>();

    public Customer() {
    }

    public Customer(String firstName, String lastName, Date birthDate, String idnp, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.idnp = idnp;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Set<CustomerAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<CustomerAccount> accounts) {
        this.accounts = accounts;
    }

    public Set<Loan> getLoans() {
        return loans;
    }

    public void setLoans(Set<Loan> loans) {
        this.loans = loans;
    }

    public CustomerInfo getInfo() {
        return info;
    }

    public void setInfo(CustomerInfo info) {
        this.info = info;
    }
}
