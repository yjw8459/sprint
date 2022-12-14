package com.yjw.sprint.tech.security;

import com.yjw.sprint.tech.entity.Member;
import lombok.experimental.Delegate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsVO implements UserDetails {

    @Delegate // 클래스에서 다른 클래스의 메서드를 위임시킨다.
    private final Member user;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsVO(Member user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getUserPw();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.isEnable();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.isEnable();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.isEnable();
    }

    @Override
    public boolean isEnabled() {
        return isEnable();
    }
}
