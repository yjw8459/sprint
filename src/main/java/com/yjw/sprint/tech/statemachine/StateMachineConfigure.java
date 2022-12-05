package com.yjw.sprint.tech.statemachine;

import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import com.yjw.sprint.tech.statemachine.event.OrderEvents;
import com.yjw.sprint.tech.statemachine.guard.OrderGuard;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory
// @RequiredArgsConstructor
public class StateMachineConfigure extends StateMachineConfigurerAdapter<OrderStatus, OrderEvents> {

    // private final StateMachineConfigurerService stateMachineConfigurerService;

    private final OrderGuard orderGuard;

//    private final OrderAction orderAction;

    public StateMachineConfigure(OrderGuard orderGuard) {
        this.orderGuard = orderGuard;
    }

    // State 선언
    @Override
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderEvents> states) throws Exception {
        // super.configure(states);
        states.withStates()
                .initial(OrderStatus.NONE)
                .state(OrderStatus.ORDER)
                .state(OrderStatus.PROCESS)
                .end(OrderStatus.CANCEL)
                .end(OrderStatus.COMP);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderEvents> transitions) throws Exception {
        // super.configure(transitions);
        transitions
                .withExternal()
                .source(OrderStatus.NONE)
                .guard(orderGuard.is())
                .target(OrderStatus.ORDER)
                .event(OrderEvents.OrderStartedEvent)
                .and()

                .withExternal()
                .source(OrderStatus.ORDER)
                .guard(orderGuard.is())
                .target(OrderStatus.PROCESS)
                .event(OrderEvents.OrderPassedEvent)
                .and()

                .withExternal()
                .source(OrderStatus.PROCESS)
                .guard(orderGuard.is())
                .target(OrderStatus.CANCEL)
//                 .action(orderAction.complete())
                .event(OrderEvents.OrderCancelEvent)
                .and()

                .withExternal()
                .source(OrderStatus.PROCESS)
                .guard(orderGuard.is())
                .target(OrderStatus.COMP)
                .event(OrderEvents.OrderCompleteEvent)
                .and();
    }
}
