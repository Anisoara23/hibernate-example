package org.example.dao;

import org.example.entity.Account;
import org.example.pojo.CustomerFinancialProfile;

import java.util.List;

public interface AccountDao {

    void addAccount(Account account);

    List<CustomerFinancialProfile> getCustomersWithAccountIds();

    void removeAccountById(String id);
}
