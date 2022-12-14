package com.yjw.sprint.tech.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인 인증이 성공할 경우 처리 핸들러
 *
 */
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.error("Login Success");
        // Token이 아닌 Session을 활용하는 경우는 SecurityContextHolder에 담아두어야함.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.sendRedirect("/main");
    }
}
