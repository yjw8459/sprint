package com.yjw.sprint.tech.statemachine.guard;

import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import com.yjw.sprint.tech.statemachine.event.OrderEvents;
import org.springframework.statemachine.guard.Guard;

public interface OrderGuard {

    public Guard<OrderStatus, OrderEvents> is();

}
