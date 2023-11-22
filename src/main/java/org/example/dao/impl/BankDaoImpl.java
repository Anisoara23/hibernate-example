package org.example.dao.impl;

import org.example.dao.BankDao;
import org.example.entity.Bank;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BankDaoImpl implements BankDao {

    private final Session session;

    public BankDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addBank(Bank bank) {
        session.save(bank);
    }

    @Override
    public Optional<Bank> getBankByCode(String code) {
        Query query = session.createQuery("SELECT b FROM Bank b WHERE b.code = :code");
        query.setParameter("code", code);
        query.setCacheable(true);
        query.setCacheRegion("bank");

        List list = query.list();

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of((Bank) query.list().get(0));
    }

    @Override
    public List<Map<String, String>> getBankCodes() {
        List<Map<String, String>> mapList = new ArrayList<>();

        Query query = session.createQuery("SELECT b FROM Bank b");
        query.setCacheable(true);
        query.setCacheRegion("bank");
        List<Bank> banks = query.list();

        banks.forEach(
                bank -> mapList.add(Map.of(bank.getCode(), bank.getName()))
        );

        return mapList;
    }
}
