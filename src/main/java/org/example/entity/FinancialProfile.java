package org.example.entity;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "financial_profile")
@Inheritance(strategy = InheritanceType.JOINED)
public class FinancialProfile {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDHexGenerator")
    @Column(name = "id")
    private String id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @Cascade(value = CascadeType.SAVE_UPDATE)
    private Branch branch;

    private BigDecimal amount;

    public FinancialProfile() {
    }

    public String getId() {
        return id;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinancialProfile that = (FinancialProfile) o;
        return Objects.equals(id, that.id) && Objects.equals(branch, that.branch) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, branch, amount);
    }
}
