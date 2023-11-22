package org.example.dao.impl;

import org.example.dao.LoanDao;
import org.example.entity.Loan;
import org.example.pojo.CustomerFinancialProfile;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class LoanDaoImpl implements LoanDao {

    private final Session session;

    public LoanDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addLoan(Loan loan) {
        session.save(loan);
    }

    @Override
    public List<CustomerFinancialProfile> getCustomersWithLoansIds() {
        Query query = session.createQuery("SELECT new org.example.pojo.CustomerFinancialProfile(" +
                "l.id," +
                "c.firstName," +
                "c.lastName," +
                "c.email," +
                "c.phoneNumber," +
                "c.idnp) " +
                "FROM Customer c " +
                "INNER JOIN c.loans as l");

        return query.list();
    }

    @Override
    public void removeLoanById(String id) {
        Query query = session.createQuery("DELETE FROM Loan l WHERE id = :id");
        query.setParameter("id", id);

        query.executeUpdate();
    }
}
