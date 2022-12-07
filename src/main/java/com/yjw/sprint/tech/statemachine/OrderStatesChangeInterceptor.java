package com.yjw.sprint.tech.statemachine;

import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import com.yjw.sprint.tech.entity.Order;
import com.yjw.sprint.tech.repository.OrderRepository;
import com.yjw.sprint.tech.statemachine.event.OrderEvents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderStatesChangeInterceptor extends StateMachineInterceptorAdapter<OrderStatus, OrderEvents> {

    private final OrderRepository orderRepository;

    @Override
    public void preStateChange(State<OrderStatus, OrderEvents> state,
                               Message<OrderEvents> message,
                               Transition<OrderStatus, OrderEvents> transition,
                               StateMachine<OrderStatus, OrderEvents> stateMachine,
                               StateMachine<OrderStatus, OrderEvents> rootStateMachine) {
        log.error("StatesChangeInterceptor");
        Optional.ofNullable(message).flatMap(msg -> Optional.ofNullable((Long) msg.getHeaders().getOrDefault("sprint", -1L)))
                .ifPresent(id -> {
                    orderRepository.findById(id).map(order -> {
                        order.setOrderStatus(state.getId());
                        Order result = orderRepository.save(order);
                        return result.toDto();
                    });
                });
    }
}
