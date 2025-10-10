package com.puzzlix.solid_task.domain.issue;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MemoryIssieRepository implements IssueRepository{

    private static Map<Long,Issue> store = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(0);

    @Override
    public Issue save(Issue issue) {
        // save 요청시 issue 에 상태 값 id 가 없는 상태이다
        if(issue.getId() == null){
            // -> 1로 변경 하고 issue 객체에 상태값 id를 1로 할당
            issue.setId(sequence.incrementAndGet());
        }
        return issue;
    }

    @Override
    public Optional<Issue> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Issue> findAll() {
        return new ArrayList<>(store.values());
    }
}
