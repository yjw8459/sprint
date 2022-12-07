package com.yjw.sprint.tech.statemachine.service;

import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import com.yjw.sprint.tech.entity.Order;
import com.yjw.sprint.tech.repository.OrderRepository;
import com.yjw.sprint.tech.statemachine.OrderStatesChangeInterceptor;
import com.yjw.sprint.tech.statemachine.event.OrderEvents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderStateEventService {

    private final OrderRepository orderRepository;
    private final StateMachineFactory<OrderStatus, OrderEvents> stateMachineFactory;
    private final OrderStatesChangeInterceptor statesChangeInterceptor;

    @Transactional
    public void changeState(Long orderId, OrderEvents events) {
        StateMachine<OrderStatus, OrderEvents> stateMachine = build(orderId);
        sendEvent(orderId, stateMachine, events);
    }

    private void sendEvent(Long orderId, StateMachine<OrderStatus, OrderEvents> stateMachine, OrderEvents events) {
        log.error("StateEventService sendEvent");
        Message<OrderEvents> msg = MessageBuilder.withPayload(events)
                .setHeader("sprint", orderId)
                .build();
        stateMachine.sendEvent(msg);    // 이벤트 발생
    }

    public StateMachine<OrderStatus, OrderEvents> build(Long orderId) {
        log.error("StateEventService build");
        Order order = orderRepository.findById(orderId).orElse(null);
        StateMachine<OrderStatus, OrderEvents> stateMachine = stateMachineFactory.getStateMachine(Long.toString(orderId));
        stateMachine.stop();
        stateMachine.getStateMachineAccessor().doWithAllRegions(sma -> sma.addStateMachineInterceptor(statesChangeInterceptor));

        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(statesChangeInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(order.getOrderStatus(), null, null, null));
                });
        stateMachine.start();
        return stateMachine;
    }
}
