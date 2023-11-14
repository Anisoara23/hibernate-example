package org.example.dao;

import org.example.entity.Branch;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BranchDao {

    void addBranch(Branch branch);

    Optional<Branch> getBranchById(Integer id);

    List<Map<Integer, String>> getBranches();
}
