package com.puzzlix.solid_task.domain.notification;

import org.springframework.stereotype.Component;

@Component
public class SmsNotificationSender implements NotificationSender{
    @Override
    public void send(String message) {
        // SMS 외부 API 연동 기능 처리
        System.out.println("SMS 알림 발송: " + message);
    }

    @Override
    public boolean supports(String type) {
        // SMS 라는 타입의 요청을 처리할 수 있다고 선언
        return "SMS".equalsIgnoreCase(type);
    }

}
