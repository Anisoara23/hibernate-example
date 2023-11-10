package org.example.dao;

import org.example.entity.Customer;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class CustomerDaoImpl implements CustomerDao {

    private final SessionFactory sessionFactory;

    public CustomerDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addCustomer(Customer customer){
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.save(customer);

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
