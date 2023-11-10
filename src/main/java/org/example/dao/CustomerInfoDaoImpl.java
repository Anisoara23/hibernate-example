package org.example.dao;

import org.example.entity.CustomerInfo;
import org.hibernate.classic.Session;

public class CustomerInfoDaoImpl implements CustomerInfoDao {

    private final Session session;

    public CustomerInfoDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addCustomerInfo(CustomerInfo customerInfo) {
        session.save(customerInfo);
    }
}
