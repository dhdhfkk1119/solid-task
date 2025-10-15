package com.puzzlix.solid_task.domain.notification;

/*
* 전략 패턴
* */
public interface NotificationSender {
    void send(String message);
    boolean supports(String type);
}
