package com.yjw.sprint.tech.statemachine.action.impl;

import com.yjw.sprint.tech.dto.OrderDTO;
import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import com.yjw.sprint.tech.entity.Order;
import com.yjw.sprint.tech.service.OrderService;
import com.yjw.sprint.tech.statemachine.action.OrderAction;
import com.yjw.sprint.tech.statemachine.event.OrderEvents;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderActionImpl implements OrderAction {

    private final OrderService orderService;

    public OrderActionImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Action<OrderStatus, OrderEvents> complete() {
        return ctx -> {
            Order order = ctx.getMessageHeaders().get("test", Order.class);
            Optional<OrderDTO> result = orderService.orderState(order.getId());
            ctx.getExtendedState().getVariables().put("Action Fail!", !result.isPresent());
        };
    }
}
