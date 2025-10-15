package com.puzzlix.solid_task.domain.notification;

import com.solapi.sdk.SolapiClient;
import com.solapi.sdk.message.exception.SolapiEmptyResponseException;
import com.solapi.sdk.message.exception.SolapiMessageNotReceivedException;
import com.solapi.sdk.message.exception.SolapiUnknownException;
import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationSender implements NotificationSender{

    DefaultMessageService messageService =
            SolapiClient.INSTANCE
                    .createInstance("NCS7OISBDF6ULLIP", "SFMLERJKS4RX1GVLW3I7FWSRO3BP7XUM");


    @Override
    public void send(String message) {
        // SMS 외부 API 연동 기능 처리
        Message messageTo = new Message();
        messageTo.setFrom("010-5207-6426");
        messageTo.setTo("010-5277-0535");
        messageTo.setText("이승민입니다");
        System.out.println("SMS 알림 발송: " + message);
        try {
            messageService.send(messageTo);
        } catch (SolapiMessageNotReceivedException e) {
            throw new RuntimeException(e);
        } catch (SolapiEmptyResponseException e) {
            throw new RuntimeException(e);
        } catch (SolapiUnknownException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean supports(String type) {
        // SMS 라는 타입의 요청을 처리할 수 있다고 선언
        return "SMS".equalsIgnoreCase(type);
    }

}
