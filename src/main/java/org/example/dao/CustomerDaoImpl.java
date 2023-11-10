package org.example.dao;

import org.example.entity.Customer;
import org.hibernate.classic.Session;

public class CustomerDaoImpl implements CustomerDao {

    private final Session session;

    public CustomerDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addCustomer(Customer customer) {
        session.save(customer);
    }
}
