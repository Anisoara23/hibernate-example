package org.example.dao;

import org.example.entity.Branch;
import org.hibernate.Query;
import org.hibernate.classic.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BranchDaoImpl implements BranchDao {

    private final Session session;

    public BranchDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addBranch(Branch branch) {
        session.save(branch);
    }

    @Override
    public Optional<Branch> getBranchById(Integer id) {
        Query query = session.createQuery("SELECT b FROM Branch b WHERE id = :id");
        query.setParameter("id", id);

        List list = query.list();

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of((Branch) list.get(0));
    }

    @Override
    public List<Map<Integer, String>> getBranches() {
        List<Map<Integer, String>> mapList = new ArrayList<>();
        Query query = session.createQuery("SELECT b FROM Branch b");
        List<Branch> branches = query.list();

        branches.forEach(
                branch -> mapList.add(Map.of(branch.getId(), branch.getName()))
        );

        return mapList;
    }
}
