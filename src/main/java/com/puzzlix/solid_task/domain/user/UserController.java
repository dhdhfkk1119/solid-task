package com.puzzlix.solid_task.domain.user;

import com.puzzlix.solid_task._global.config.jwt.JwtTokenProvider;
import com.puzzlix.solid_task._global.dto.CommonResponseDto;
import com.puzzlix.solid_task.domain.user.dto.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto<?>> signUp(@Valid @RequestBody UserRequest.SignUp request){
        userService.signUp(request);
        return ResponseEntity.ok(CommonResponseDto.success(null,"회원 가입 성공"));
    }

    @PostMapping("/login/{type}")
    public ResponseEntity<CommonResponseDto<?>> loginUp(@PathVariable("type") String type,
                                                        @Valid @RequestBody UserRequest.Login request) {
        User user = userService.login(type,request);
        String token = jwtTokenProvider.createToken(user);
        return ResponseEntity.ok(CommonResponseDto.success(token,"로그인 성공"));
    }
}
