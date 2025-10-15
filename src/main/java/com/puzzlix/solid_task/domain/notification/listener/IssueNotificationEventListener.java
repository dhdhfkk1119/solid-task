package com.puzzlix.solid_task.domain.notification.listener;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.event.IssueStatusChangedEvent;
import com.puzzlix.solid_task.domain.notification.NotificationSenderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IssueNotificationEventListener {
    private final NotificationSenderFactory notificationSenderFactory;

    // 알림 정책 - yml 파일 -> EMAIL
    // 값을 읽어 와서 변수 주입
    @Value("${notification.policy.on-status-done}")
    private String onStatusDoneType;

    @EventListener
    public void handleIssueStatusChangeEvent(IssueStatusChangedEvent event){
        Issue issue = event.getIssue();
        String message = "이슈가" + issue.getTitle() + "의 상태가" + issue.getIssueStatus() + "변경되었습니다";

        if("DONE".equalsIgnoreCase(issue.getIssueStatus().name())){
            notificationSenderFactory.findSender(onStatusDoneType).send(message);
        }
    }
}
