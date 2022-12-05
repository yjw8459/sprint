package com.yjw.sprint.tech.statemachine.action;

import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import com.yjw.sprint.tech.statemachine.event.OrderEvents;
import org.springframework.statemachine.action.Action;

public interface OrderAction {

    public Action<OrderStatus, OrderEvents> complete();
}
