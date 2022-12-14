package com.yjw.sprint.tech.security;

import com.yjw.sprint.tech.security.handler.LoginFailureHandler;
import com.yjw.sprint.tech.security.handler.LoginSuccessHandler;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security 관련 Configuration
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public LoginSuccessHandler successHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public LoginFailureHandler failureHandler() {
        return new LoginFailureHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MemberAuthenticationFilter authenticationFilter() throws Exception{
        MemberAuthenticationFilter memberAuthenticationFilter = new MemberAuthenticationFilter(authenticationManager());
        memberAuthenticationFilter.setFilterProcessesUrl("/login");
        memberAuthenticationFilter.setAuthenticationSuccessHandler(successHandler());
        memberAuthenticationFilter.setAuthenticationFailureHandler(failureHandler());
        return memberAuthenticationFilter;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// Session을 사용하지 않고 JWT토큰을 활용한 진행, csrf 토큰 검사를 비활성화
                .authorizeRequests() // 인증 절차에 대한 설정을 진행
                .antMatchers("/api", "/error", "/login", "/loginProc").permitAll() // 설정한 url 인증절차 제외
                .anyRequest().authenticated() // 위 페이지 외 인증이 되어야 접근 가능
                .and()
                .formLogin()
                // .loginPage("/login")
                .loginProcessingUrl("/loginProc") // login Process url
                .usernameParameter("memberId")    // id Parameter Name
                .passwordParameter("password")    // password Parameter Name
                // .successHandler(successHandler()) // login success Handler
                // .failureHandler(failureHandler()) // login Fail Handler
                .permitAll()
                .and()
                .logout()   // logout configuration
                .logoutUrl("/logout") // logout url
                .logoutSuccessUrl("/logout/success") // logout Handler
                .invalidateHttpSession(true)    // Session Clear
                ;
    }
}
