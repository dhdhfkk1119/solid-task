package com.puzzlix.solid_task.domain;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.IssueRepository;
import com.puzzlix.solid_task.domain.issue.IssueService;
import com.puzzlix.solid_task.domain.issue.IssueStatus;
import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.project.Project;
import com.puzzlix.solid_task.domain.project.ProjectRepository;
import com.puzzlix.solid_task.domain.user.User;
import com.puzzlix.solid_task.domain.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // 가짜 객체를 만들어줌
public class IssueServiceTest {
    @Mock
    private IssueRepository issueRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks // Mock 메모리에 띄워 줘야함
    private IssueService issueService;

    /*
    * 테스트 메서드 : 새로운 이슈 생성시 기능 테스트
    * 테스트 시나리오
    * 1. 클라이언트가 이슈 생성 요청 보냄
    * 2. 서비스는 필요한 연관 엔티티드를 조회 함
    * 3. 새로운 Issue 객체를 생성하고 연관관계 설정해
    * 4. Issue를 저장함
    * */
    @Test
    @DisplayName("새로운 이슈를 성공적으로 생성한다(연관관계 포함)")
    void createIssue_withMapping(){
        IssueRequest.Create create = new IssueRequest.Create();
        create.setTitle("이슈 생성 테스트 확인");
        create.setDescription("단위 테스트 통과 여부 확인");
        create.setProjectId(1L);
        create.setReporterId(1L);

        // 2. Repository 조회 시 반환 될 가짜 객체 엔티티 준비
        User mockReporter = new User(1L,"조정우","test@test.com","1234",null);
        Project mockProject = new Project(1L,"SOLID Task 프로젝트","SOLID 개념 학습",null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(mockReporter)); // 유저 조회
        when(projectRepository.findById(1L)).thenReturn(Optional.of(mockProject)); // 프로젝트 조회
        when(issueRepository.save(any(Issue.class))).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        Issue createIssue = issueService.createIssue(create);

        Assertions.assertThat(createIssue.getTitle()).isEqualTo("이슈 생성 테스트 확인");
        Assertions.assertThat(createIssue.getIssueStatus()).isEqualTo((IssueStatus.TODO));

        // 연관관계가 올바르게 설정 되었는지 객체 자체를 비교해서 검증
        Assertions.assertThat(createIssue.getReporter()).isEqualTo(mockReporter);
        Assertions.assertThat(createIssue.getProject()).isEqualTo(mockProject);

        // 추가 검증 - 서비스 로직이 실제로 findById를 호출하는지
        verify(userRepository).findById(1L);
        verify(projectRepository).findById(1L);
    }


}
