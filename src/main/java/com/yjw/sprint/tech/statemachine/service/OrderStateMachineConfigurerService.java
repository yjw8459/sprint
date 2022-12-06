package com.yjw.sprint.tech.statemachine.service;

import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import com.yjw.sprint.tech.statemachine.event.OrderEvents;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;

import javax.annotation.PostConstruct;

@AllArgsConstructor
@Slf4j
public class OrderStateMachineConfigurerService {

    private final StateMachine<OrderStatus, OrderEvents> stateMachine;

    @PostConstruct
    private void init(){
        stateMachine.start();
        log.info("Order State Machine Start");
    }

}
