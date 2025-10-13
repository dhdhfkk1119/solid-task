package com.puzzlix.solid_task.domain.user.login;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginStrategyFactory{
    private final List<LoginStrategy> strategies;

    public LoginStrategy findStrategy(String type){
        for(LoginStrategy strategy : strategies){
            if(strategy.supports(type)){
                return strategy;
            }
        }
        // 만약 for 문에 다 돌때 까지 지원하는 전략 구현 클래스를 찾지 못했다면 예외 발생 처리
        throw new IllegalArgumentException("지원하지 않는 로그인 방식입니다");
    }

}
