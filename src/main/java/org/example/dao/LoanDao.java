package org.example.dao;

import org.example.entity.Loan;
import org.example.pojo.CustomerFinancialProfile;

import java.util.List;

public interface LoanDao {

    void addLoan(Loan loan);

    List<CustomerFinancialProfile> getCustomersWithLoansIds();

    void removeLoanById(String id);
}
