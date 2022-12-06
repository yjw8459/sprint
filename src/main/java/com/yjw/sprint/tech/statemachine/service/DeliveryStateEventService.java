package com.yjw.sprint.tech.statemachine.service;

import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import com.yjw.sprint.tech.entity.Order;
import com.yjw.sprint.tech.repository.OrderRepository;
import com.yjw.sprint.tech.statemachine.DeliveryStatesChangeInterceptor;
import com.yjw.sprint.tech.statemachine.event.DeliveryEvents;
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

import java.awt.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryStateEventService {

    private final OrderRepository orderRepository;

    private final StateMachineFactory<DeliveryStatus, DeliveryEvents> stateMachineFactory;

    private final DeliveryStatesChangeInterceptor statesChangeInterceptor;

    @Transactional
    public void changeState(Long orderId, DeliveryEvents events) {
        StateMachine<DeliveryStatus, DeliveryEvents> stateMachine = build(orderId);
        sendEvent(orderId, stateMachine, events);
    }

    private void sendEvent(Long orderId,
                           StateMachine<DeliveryStatus, DeliveryEvents> stateMachine,
                           DeliveryEvents events) {
        log.info("Delivery SendEvent");
        Message<DeliveryEvents> msg = MessageBuilder.withPayload(events)
                .setHeader("sprint", orderId)
                .build();
        stateMachine.sendEvent(msg);
    }

    private StateMachine<DeliveryStatus, DeliveryEvents> build(Long orderId) {
        log.info("Delivery State Event Service Build");
        Order order = orderRepository.findById(orderId).orElse(null);
        StateMachine<DeliveryStatus, DeliveryEvents> stateMachine = stateMachineFactory.getStateMachine(Long.toString(orderId));
        stateMachine.stop();
        stateMachine.getStateMachineAccessor().doWithAllRegions(sma -> sma.addStateMachineInterceptor(statesChangeInterceptor));

        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(statesChangeInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(order.getDeliveryStatus(), null, null, null));
                });
        stateMachine.start();
        return stateMachine;
    }


}
