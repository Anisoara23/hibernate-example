package org.example.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "customer_account")
@AssociationOverrides({
        @AssociationOverride(name = "id.customer",
                joinColumns = @JoinColumn(name = "customer_id")),
        @AssociationOverride(name = "id.account",
                joinColumns = @JoinColumn(name = "account_code"))
})
public class CustomerAccount {

    @EmbeddedId
    private CustomerAccountId id = new CustomerAccountId();

    @Column(name = "created_at", nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date createdAt;

    public CustomerAccount() {
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setCustomer(Customer customer){
        id.setCustomer(customer);
    }

    @Transient
    public Customer getCustomer(){
        return id.getCustomer();
    }

    public void setAccount(Account account){
        id.setAccount(account);
    }

    @Transient
    public Account getAccount(){
        return id.getAccount();
    }
}
