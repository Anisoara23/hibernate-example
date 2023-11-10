package org.example.dao;

import org.example.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    void addCustomer(Customer customer);

    Optional<Customer> getCustomerByEmail(String email);

    List<String> getEmails();
}
