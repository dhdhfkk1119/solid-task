package com.puzzlix.solid_task.domain.issue;

import com.puzzlix.solid_task._global.dto.CommonResponseDto;
import com.puzzlix.solid_task.domain.issue.dto.IssueRequest;
import com.puzzlix.solid_task.domain.issue.dto.IssueResponse;
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
    public ResponseEntity<CommonResponseDto<Issue>> createIssue(@RequestBody IssueRequest.Create request){
        Issue createdIssue = issueService.createIssue(request);
        return ResponseEntity.status(HttpStatus.CREATED).
                body(CommonResponseDto.success(createdIssue));
    }

    @GetMapping
    public ResponseEntity<CommonResponseDto<List<IssueResponse.FindAll>>> getIssues(){
        List<Issue> issues = issueService.findIssues();
        List<IssueResponse.FindAll> responseDtos = IssueResponse.FindAll.from(issues);
        return ResponseEntity.ok(CommonResponseDto.success(responseDtos));
    }

}
