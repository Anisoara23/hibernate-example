package org.example.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "financial_profile")
@Inheritance(strategy = InheritanceType.JOINED)
public class FinancialProfile {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDHexGenerator")
    @Column(name = "id")
    private String id;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    private Branch branch;

    private BigDecimal amount;

    public FinancialProfile() {
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
