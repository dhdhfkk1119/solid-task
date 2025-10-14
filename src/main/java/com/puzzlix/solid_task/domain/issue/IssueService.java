package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IssueService {
    // 구체 클래스가 아닌 , IssueRepository 라는 역할 (인터페이스)에만 의존한다
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public Issue updateIssue(Long issueId , IssueRequest.Update request){
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당 이슈가 존재하지 없습니다"));

        if(request.getAssignId() != null) {
            User assignee = userRepository.findById(request.getAssignId())
                    .orElseThrow(() -> new NoSuchElementException("해당 ID에 담당자를 찾을 수없습니다"));
            issue.setAssignee(assignee);
        } else {
            issue.setAssignee(null);
        }

        issue.setTitle(request.getTitle());
        issue.setDescription(request.getDescription());
        return issue;


    }

    public void deleteIssue(Long issueId){
        if(!issueRepository.existsById(issueId)){
            throw new NoSuchElementException("해당 ID를 찾을 수 없습니다");
        }
        issueRepository.deleteById(issueId);
    }



    public Issue createIssue(IssueRequest.Create request){

        User reporter = userRepository.findById(request.getReporterId())
                .orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수 없습니다"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new NoSuchElementException("해당 게시물을 찾을 수 없습니다"));



        Issue newIssue = new Issue();
        newIssue.setTitle(request.getTitle());
        newIssue.setDescription(request.getDescription());
        newIssue.setIssueStatus(IssueStatus.TODO);
        newIssue.setReporter(reporter);
        newIssue.setProject(project);
        return issueRepository.save(newIssue);
    }

    public List<Issue> findIssues(){
        return issueRepository.findAll();
    }
}
