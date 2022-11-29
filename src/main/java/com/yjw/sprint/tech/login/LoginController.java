package com.yjw.sprint.tech.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequestMapping(value = "/login")
public class LoginController {
    
    @GetMapping
    public ModelAndView login(){
        return new ModelAndView("/login");
    }
    
}
