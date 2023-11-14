package org.example.dao;

import org.example.entity.Account;
import org.example.pojo.CustomerFinancialProfile;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import java.util.List;

public class AccountDaoImpl implements AccountDao {

    private final Session session;

    public AccountDaoImpl(Session session) {
        this.session = session;
    }

    public void addAccount(Account account) {
        session.save(account);
    }

    @Override
    public List<CustomerFinancialProfile> getCustomersWithAccountIds() {
        Query query = session.createQuery("SELECT new org.example.pojo.CustomerFinancialProfile(" +
                "a.id.account.id," +
                "c.firstName," +
                "c.lastName," +
                "c.email," +
                "c.phoneNumber," +
                "c.idnp) " +
                "FROM Customer c " +
                "INNER JOIN c.accounts as a");

        return query.list();
    }

    @Override
    public void removeAccountById(String id) {
        Query selectAccount = session.createQuery("SELECT a FROM Account a WHERE id = :id");
        selectAccount.setParameter("id", id);

        if (!selectAccount.list().isEmpty()) {
            Account account = (Account) selectAccount.list().get(0);
            session.delete(account);
        }
    }
}
