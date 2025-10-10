package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssueService {
    // 구체 클래스가 아닌 , IssueRepository 라는 역할 (인터페이스)에만 의존한다
    private final IssueRepository issueRepository;

    public Issue createIssue(IssueRequest.Create request){
        Issue newIssue = new Issue();
        newIssue.setTitle(request.getTitle());
        newIssue.setDescription(request.getDescription());
        newIssue.setIssueStatus(IssueStatus.TODO);
        newIssue.setReportId(request.getReporterId());
        newIssue.setProjectId(request.getProjectId());
        return issueRepository.save(newIssue);
    }

    public List<Issue> findIssues(){
        return issueRepository.findAll();
    }
}
