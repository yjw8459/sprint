package com.yjw.sprint.tech.security;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String userName) {
        super(userName + "회원을 찾지 못하였습니다.");
    }

}
