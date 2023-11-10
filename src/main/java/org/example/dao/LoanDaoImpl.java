package org.example.dao;

import org.example.entity.Loan;
import org.hibernate.classic.Session;

public class LoanDaoImpl implements LoanDao {

    private final Session session;

    public LoanDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addLoan(Loan loan) {
        session.save(loan);
    }
}
