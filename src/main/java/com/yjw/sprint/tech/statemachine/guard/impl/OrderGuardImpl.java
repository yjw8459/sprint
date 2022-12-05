package com.yjw.sprint.tech.statemachine.guard.impl;

import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import com.yjw.sprint.tech.statemachine.event.OrderEvents;
import com.yjw.sprint.tech.statemachine.guard.OrderGuard;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.guard.Guard;

@Configuration
public class OrderGuardImpl implements OrderGuard {

    @Override
    public Guard<OrderStatus, OrderEvents> is() {
        return ctx -> Boolean.TRUE;
    }
}
