package com.puzzlix.solid_task.domain.issue;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue,Long> {

    Issue save(Issue issue);
    Optional<Issue> findById(Long id);
    List<Issue> findAll();
}
