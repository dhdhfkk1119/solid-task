package com.puzzlix.solid_task.domain.issue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Issue {

    private Long id;

    private String title;

    private String description;

    private IssueStatus issueStatus;

    private Long projectId;
    private Long reportId;
    private Long assigneeId;
}
