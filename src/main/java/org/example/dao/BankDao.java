package org.example.dao;

import org.example.entity.Bank;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BankDao {

    void addBank(Bank bank);

    Optional<Bank> getBankByCode(String code);

    List<Map<String, String>> getBankCodes();
}
