package com.puzzlix.solid_task.domain.issue;

import java.util.List;
import java.util.Optional;

public interface IssueRepository {

    Issue save(Issue issue);
    Optional<Issue> findById(Long id);
    List<Issue> findAll();
}
