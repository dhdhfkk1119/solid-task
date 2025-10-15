package com.puzzlix.solid_task.domain.user;

import com.puzzlix.solid_task.domain.user.dto.UserRequest;
import com.puzzlix.solid_task.domain.user.login.LoginStrategy;
import com.puzzlix.solid_task.domain.user.login.LoginStrategyFactory;
import com.puzzlix.solid_task.domain.user.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginStrategyFactory factory;

    @Transactional
    public User signUp(UserRequest.SignUp request){
        // 이메일 중복확인
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이메일이 중복됩니다.");
        }


        String encodePassword = passwordEncoder.encode(request.getPassword());
        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encodePassword);
        newUser.setRole(request.getRole() != null ? request.getRole() : Role.USER);
        return userRepository.save(newUser);
    }

    public User login(String type,UserRequest.Login request) {
        
        // 팩토리에 알맞은 로그인 전략을 요청
        LoginStrategy loginStrategy = factory.findStrategy(type);
        
        // 해당 전략을 선택하여 로그인 요청 완료
        return loginStrategy.login(request);
    }
}
