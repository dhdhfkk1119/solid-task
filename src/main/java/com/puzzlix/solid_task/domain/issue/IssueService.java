package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task._global.config.jwt.JwtInterceptor;
import com.puzzlix.solid_task._global.config.jwt.JwtTokenProvider;
import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.issue.event.IssueStatusChangedEvent;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import com.puzzlix.solid_task.domain.user.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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

    // 이벤트 발생자 (스프링이 제공)
    // 이 객체를 통해서 애플리케이션의 다른 부분에 "어떤 이벤트가 발생했다" 라는 것을 알리 수 있다
    private final ApplicationEventPublisher eventPublisher;


    public Issue updateIssueStatus(Long issueId,IssueStatus issueStatus,String userEmail,Role userRole){
        Issue issue = issueRepository.findById(issueId).orElseThrow(() -> new NoSuchElementException("해당 이슈를 찾을 수 없습니다"));
        // 관리자가 아니거나 담당자가 아니면 상태를 변경 못함
        if(userRole != Role.ADMIN && !issue.getAssignee().getEmail().equals(userEmail)){
            throw new SecurityException("이슈를 수정할 권한이 없습니다");
        }
        issue.setIssueStatus(issueStatus);
        if(issueStatus == IssueStatus.DONE){
            // 이벤트 발생
            eventPublisher.publishEvent(new IssueStatusChangedEvent(issue));
        }
        return issue;
    }

    public Issue updateIssue(Long issueId , IssueRequest.Update request,String userEmail){

        User requestUser = userRepository.findByEmail(userEmail).orElseThrow(() -> new NoSuchElementException("해당 유저를 찾을 수없습니다"));

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당 이슈가 존재하지 없습니다"));

        boolean isAdmin = requestUser.getRole() == Role.ADMIN;
        boolean isOwner = requestUser.getId().equals(issue.getReporter().getId());

        if(isAdmin == false && isOwner == false) {
            throw new SecurityException("이슈를 수정할 권한이 없습니다");
        }


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

    // 인가 처리 해주세요 ADMIN 만 삭제 가능
    public void deleteIssue(Long issueId,String userEmail,Role userRole){


        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new NoSuchElementException("해당 이슈를 찾을 수 없습니다"));

        if(!issue.getReporter().getEmail().equals(userEmail) && userRole != Role.ADMIN){
            throw new SecurityException("어드민 또는 보고자만 삭제 가능합니다");
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
