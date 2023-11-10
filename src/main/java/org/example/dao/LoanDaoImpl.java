package org.example.dao;

import org.example.entity.Loan;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class LoanDaoImpl implements LoanDao {

    private final SessionFactory sessionFactory;

    public LoanDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addLoan(Loan loan){
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.save(loan);

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
