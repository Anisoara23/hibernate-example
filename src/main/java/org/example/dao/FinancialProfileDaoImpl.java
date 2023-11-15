package org.example.dao;

import org.hibernate.Query;
import org.hibernate.classic.Session;

import java.math.BigDecimal;

public class FinancialProfileDaoImpl implements FinancialProfileDao {

    private final Session session;

    public FinancialProfileDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public BigDecimal getLoanAmount(String id) {
        Query query = session.createQuery("SELECT fp.amount FROM FinancialProfile fp WHERE id = :id");
        query.setParameter("id", id);

        if (!query.list().isEmpty()) {
            return (BigDecimal) query.list().get(0);
        }

        return null;
    }

    @Override
    public int updateAmount(String id, BigDecimal newAmount) {
        Query query = session.createQuery("UPDATE FinancialProfile SET amount = :amount WHERE id = :id");
        query.setParameter("id", id);
        query.setParameter("amount", newAmount);

        return query.executeUpdate();
    }
}
