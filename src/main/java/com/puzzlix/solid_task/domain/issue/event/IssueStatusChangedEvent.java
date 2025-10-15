package com.puzzlix.solid_task.domain.issue.event;

import com.puzzlix.solid_task.domain.issue.Issue;
import lombok.Data;

// 스프링에서 제공하는 스프링 이벤트를 사용할 예정

@Data
public class IssueStatusChangedEvent {
    private final Issue issue;

    public IssueStatusChangedEvent(Issue issue) {
        this.issue = issue;
    }
}
