package org.example.dao;

import org.example.entity.Branch;
import org.hibernate.classic.Session;

public class BranchDaoImpl implements BranchDao {

    private final Session session;

    public BranchDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void addBranch(Branch branch) {
        session.save(branch);
    }
}
