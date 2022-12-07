package com.yjw.sprint.tech.statemachine;

import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.entity.Order;
import com.yjw.sprint.tech.repository.OrderRepository;
import com.yjw.sprint.tech.statemachine.event.DeliveryEvents;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeliveryStatesChangeInterceptor extends StateMachineInterceptorAdapter<DeliveryStatus, DeliveryEvents> {

    private final OrderRepository orderRepository;



    // 3
    @Override
    public Message<DeliveryEvents> preEvent(Message<DeliveryEvents> message, StateMachine<DeliveryStatus, DeliveryEvents> stateMachine) {
        log.error("Delivery Interceptor preEvent");
        return super.preEvent(message, stateMachine);
    }

    // 6

    /**
     * State 변경 시 저장
     * @param state the state
     * @param message the message
     * @param transition the transition
     * @param stateMachine the state machine
     * @param rootStateMachine the root state machine
     */
    @Override
    public void preStateChange(State<DeliveryStatus, DeliveryEvents> state,
                               Message<DeliveryEvents> message,
                               Transition<DeliveryStatus, DeliveryEvents> transition,
                               StateMachine<DeliveryStatus, DeliveryEvents> stateMachine,
                               StateMachine<DeliveryStatus, DeliveryEvents> rootStateMachine) {
        log.error("Delivery Interceptor preStateChange");
//        Optional.ofNullable(message).flatMap(msg -> Optional.ofNullable((Long) msg.getHeaders().getOrDefault("sprint", -1L)))
//                .ifPresent(id -> {
//                    orderRepository.findById(id).map(order -> {
//                        order.setDeliveryStatus(state.getId());
//                        Order result = orderRepository.save(order);
//                        return result.toDto();
//                    });
//                });
    }

    // 7
    @Override
    public void postStateChange(State<DeliveryStatus, DeliveryEvents> state, Message<DeliveryEvents> message, Transition<DeliveryStatus, DeliveryEvents> transition, StateMachine<DeliveryStatus, DeliveryEvents> stateMachine, StateMachine<DeliveryStatus, DeliveryEvents> rootStateMachine) {
        log.error("Delivery Interceptor postStateChange");
        super.postStateChange(state, message, transition, stateMachine, rootStateMachine);
    }

    // 4
    @Override
    public StateContext<DeliveryStatus, DeliveryEvents> preTransition(StateContext<DeliveryStatus, DeliveryEvents> stateContext) {
        log.error("Delivery Interceptor preTransition");
        return super.preTransition(stateContext);
    }

    // 8
    @Override
    public StateContext<DeliveryStatus, DeliveryEvents> postTransition(StateContext<DeliveryStatus, DeliveryEvents> stateContext) {
        log.error("Delivery Interceptor postTransition");
        return super.postTransition(stateContext);
    }

    @Override
    public Exception stateMachineError(StateMachine<DeliveryStatus, DeliveryEvents> stateMachine, Exception exception) {
        log.error("Delivery Interceptor stateMachineError");
        return super.stateMachineError(stateMachine, exception);
    }
}
