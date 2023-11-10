package org.example.dao;

import org.example.entity.CustomerInfo;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

public class CustomerInfoDaoImpl implements CustomerInfoDao {

    private final SessionFactory sessionFactory;

    public CustomerInfoDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addCustomerInfo(CustomerInfo customerInfo){
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            session.save(customerInfo);

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
