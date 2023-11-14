package org.example.dao;

import org.example.entity.Loan;
import org.example.pojo.CustomerFinancialProfile;
import org.hibernate.Query;
import org.hibernate.classic.Session;

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
        Query selectQuery = session.createQuery("SELECT l FROM Loan l WHERE id = :id");
        selectQuery.setParameter("id", id);

        if (!selectQuery.list().isEmpty()) {
            Loan loan = (Loan) selectQuery.list().get(0);
            loan.removeCustomersAssociation();
            session.delete(loan);
        }
    }
}
