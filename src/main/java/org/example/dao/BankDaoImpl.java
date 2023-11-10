package org.example.dao;

import org.example.entity.Bank;
import org.hibernate.classic.Session;

public class BankDaoImpl implements BankDao {

    private final Session session;

    public BankDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addBank(Bank bank){
        session.save(bank);
    }
}
