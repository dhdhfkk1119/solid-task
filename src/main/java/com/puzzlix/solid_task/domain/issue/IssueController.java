package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task._global.dto.CommonResponseDto;
import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.issue.dto.IssueResponse;
import com.puzzlix.solid_task.domain.user.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
@RequiredArgsConstructor
public class IssueController {
    private final IssueService issueService;

    /*
     * 이슈 생성 API
     * POST / api/issues*/
    @PostMapping
    public ResponseEntity<CommonResponseDto<Issue>> createIssue(@RequestBody IssueRequest.Create request) {
        Issue createdIssue = issueService.createIssue(request);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(CommonResponseDto.success(createdIssue));
    }

    @PatchMapping("/{issueId}/status")
    public ResponseEntity<CommonResponseDto<Issue>> updateIssueStatus(@PathVariable("issueId") Long issueId,
                                                                      @RequestParam("status") IssueStatus issueStatus,
                                                                      @RequestAttribute("userEmail") String userEmail,
                                                                      @RequestAttribute("userRole") Role userRole){

        Issue issue = issueService.updateIssueStatus(issueId,issueStatus,userEmail,userRole);

        return ResponseEntity.ok(CommonResponseDto.success(issue,"성공적으로 변경되었습니다"));
    }

    @PutMapping("/{issueId}")
    public ResponseEntity<CommonResponseDto<IssueResponse.FindById>> updateIssue(@PathVariable("issueId") Long issuedId,
                                                                                 @RequestBody IssueRequest.Update update,
                                                                                 @RequestAttribute("userEmail") String userEmail) {
        Issue issue = issueService.updateIssue(issuedId, update, userEmail);
        return ResponseEntity.ok(CommonResponseDto.success(new IssueResponse.FindById(issue), "성공적으로 변경되었습니다"));
    }


    /*
     * 이슈 삭제 API
     * DELETE /api/issues/{issueId}
     * */
    @DeleteMapping("/{issueId}")
    public ResponseEntity<CommonResponseDto<?>> deleteIssue(@PathVariable("issueId") Long issueId,
                                                            @RequestAttribute("userEmail") String userEmail,
                                                            @RequestAttribute("userRole") Role userRole) {


        issueService.deleteIssue(issueId, userEmail, userRole);
        return ResponseEntity.ok(CommonResponseDto.success(null, "성공적으로 삭제되었습니다"));
    }


    @GetMapping
    public ResponseEntity<CommonResponseDto<List<IssueResponse.FindAll>>> getIssues() {
        List<Issue> issues = issueService.findIssues();
        List<IssueResponse.FindAll> responseDtos = IssueResponse.FindAll.from(issues);
        return ResponseEntity.ok(CommonResponseDto.success(responseDtos));
    }


}
