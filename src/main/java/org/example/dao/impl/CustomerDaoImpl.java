package org.example.dao.impl;

import org.example.dao.CustomerDao;
import org.example.entity.Customer;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {

    private final Session session;

    public CustomerDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addCustomer(Customer customer) {
        session.save(customer);
    }

    @Override
    public Optional<Customer> getCustomerByEmail(String email) {
        Query query = session.createQuery("SELECT c FROM Customer c WHERE c.email = :email");
        query.setParameter("email", email);
        query.setCacheable(true);
        query.setCacheRegion("customer");

        List list = query.list();

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of((Customer) list.get(0));
    }

    @Override
    public List<String> getEmails() {
        Query query = session.createQuery("SELECT c.email FROM Customer c");
        query.setCacheable(true);
        query.setCacheRegion("customer");

        return query.list();
    }
}
