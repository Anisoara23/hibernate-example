package org.example.dao.impl;

import org.example.dao.CustomerInfoDao;
import org.example.entity.CustomerInfo;
import org.hibernate.Session;

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
