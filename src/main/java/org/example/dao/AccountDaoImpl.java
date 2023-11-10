package org.example.dao;

import org.example.entity.Account;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class AccountDaoImpl implements AccountDao {

    private final SessionFactory sessionFactory;

    public AccountDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addAccount(Account account) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.save(account);

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
