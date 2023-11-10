package org.example.dao;

import org.example.entity.Bank;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class BankDaoImpl implements BankDao {

    private final SessionFactory sessionFactory;

    public BankDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addBank(Bank bank){
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.save(bank);

            transaction.commit();
        } catch (RuntimeException exception) {
            try {
                transaction.rollback();
            } catch (RuntimeException rollbackException) {
                System.err.println("Couldn’t roll back transaction " + rollbackException);
            }
            System.err.println(exception.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
