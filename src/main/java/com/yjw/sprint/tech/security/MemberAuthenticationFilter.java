package com.yjw.sprint.tech.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 요청이 오면 AuthenticationFilter로 요청이 먼저 오고, 아이디와 비밀번호 기반으로 UserPasswordAuthenticationToken을 발급한다.
 *
 */

@Slf4j
public class MemberAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public MemberAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(request.getParameter("memberId"), request.getParameter("password"));
        setDetails(request, authRequest);
        return super.attemptAuthentication(request, response);
    }
}
