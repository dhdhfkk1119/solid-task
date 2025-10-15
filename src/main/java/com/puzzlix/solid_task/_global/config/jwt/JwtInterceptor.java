package com.puzzlix.solid_task._global.config.jwt;

import ch.qos.logback.core.util.StringUtil;
import com.puzzlix.solid_task.domain.user.role.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = resolveToken(request);
        if(token != null && jwtTokenProvider.validateToken(token)){
            String userEmail = jwtTokenProvider.getSubject(token);
            Role role = jwtTokenProvider.getRole(token);

            request.setAttribute("userRole",role);
            request.setAttribute("userEmail",userEmail);
            return true;
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"유효하지 않은 토큰입니다");
        return false;
    }

    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        // 토큰 검증
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }

}
