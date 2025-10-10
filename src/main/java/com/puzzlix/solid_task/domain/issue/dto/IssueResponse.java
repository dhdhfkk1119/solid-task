package com.puzzlix.solid_task.domain.issue.dto;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class IssueResponse {

    @Data
    public static class FindAll{
        private final Long id;
        private final String title;
        private final IssueStatus issueStatus;
        private final String reporterName;

        // 생성자를 private 선언
        private FindAll(Issue issue) {
            this.id = issue.getId();
            this.title = issue.getTitle();
            this.issueStatus = issue.getIssueStatus();
            this.reporterName = issue.getReporter().getName();
        }

        // 정적 팩토리 메서드 선언(이녀석은 제네릭이 아님)
        public static List<FindAll> from(List<Issue> issues){
            List<FindAll> dtoList = new ArrayList<>();
            for(Issue issue : issues){
                dtoList.add(new FindAll(issue));
            }
            return dtoList;
        }
    }
}
