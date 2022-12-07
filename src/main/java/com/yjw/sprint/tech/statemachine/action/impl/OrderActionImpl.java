package com.yjw.sprint.tech.statemachine.action.impl;

import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import com.yjw.sprint.tech.statemachine.action.OrderAction;
import com.yjw.sprint.tech.statemachine.event.OrderEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderActionImpl implements OrderAction {

    @Override
    public Action<OrderStatus, OrderEvents> orderAction(OrderStatus orderStatus) {
        return context -> {
            log.error("orderState :{}", orderStatus);
        };
    }

}
