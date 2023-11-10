package org.example.dao;

import org.example.entity.Account;
import org.hibernate.classic.Session;

public class AccountDaoImpl implements AccountDao {

    private final Session session;

    public AccountDaoImpl(Session session) {
        this.session = session;
    }

    public void addAccount(Account account) {
        session.save(account);
    }
}
