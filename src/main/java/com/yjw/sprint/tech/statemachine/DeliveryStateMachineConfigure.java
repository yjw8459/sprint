package com.yjw.sprint.tech.statemachine;

import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.statemachine.action.DeliveryAction;
import com.yjw.sprint.tech.statemachine.event.DeliveryEvents;
import com.yjw.sprint.tech.statemachine.guard.DeliveryGuard;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

@Configuration
@EnableStateMachineFactory(name = "deliveryStateMachineFactory")
public class DeliveryStateMachineConfigure extends StateMachineConfigurerAdapter<DeliveryStatus, DeliveryEvents> {

    private DeliveryGuard guard;

    private DeliveryAction action;

    public DeliveryStateMachineConfigure(DeliveryGuard guard, DeliveryAction action) {
        this.guard = guard;
        this.action = action;
    }

    @Override
    public void configure(StateMachineStateConfigurer<DeliveryStatus, DeliveryEvents> states) throws Exception {
        // super.configure(states);
        states.withStates()
                .initial(DeliveryStatus.ORDER)
                .state(DeliveryStatus.WAIT)
                .state(DeliveryStatus.SHIP_PROCESS)
                .state(DeliveryStatus.SHIPPING)
                .end(DeliveryStatus.COMPLETE)
                .end(DeliveryStatus.CANCEL);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<DeliveryStatus, DeliveryEvents> transitions) throws Exception {
        // super.configure(transitions);
        transitions
                
                // 주문 -> 배송 대기
                .withExternal()
                .source(DeliveryStatus.ORDER)
                .target(DeliveryStatus.WAIT)
                .event(DeliveryEvents.StartDelivery)
                .guard(guard.is())
                .action(action.action(DeliveryEvents.StartDelivery))
                .and()

                // 배송 대기 -> 배송 처리중
                .withExternal()
                .source(DeliveryStatus.WAIT)
                .target(DeliveryStatus.SHIP_PROCESS)
                .event(DeliveryEvents.StartShipProcess)
                .guard(guard.is())
                .action(action.action(DeliveryEvents.StartShipProcess))
                .and()

                // 배송 처리중 -> 배송중
                .withExternal()
                .source(DeliveryStatus.SHIP_PROCESS)
                .target(DeliveryStatus.SHIPPING)
                .event(DeliveryEvents.StartShipping)
                .guard(guard.is())
                .action(action.action(DeliveryEvents.StartShipping))
                .and()

                // 배송중 -> 배송완료
                .withExternal()
                .source(DeliveryStatus.SHIPPING)
                .target(DeliveryStatus.COMPLETE)
                .event(DeliveryEvents.EndShipping)
                .guard(guard.is())
                .action(action.action(DeliveryEvents.EndShipping))
                .and()
                
                // 배송 대기 -> 주문 취소
                .withExternal()
                .source(DeliveryStatus.WAIT)
                .target(DeliveryStatus.CANCEL)
                .event(DeliveryEvents.CancelOrder)
                .guard(guard.is())
                .action(action.action(DeliveryEvents.CancelOrder))
                .and()

                // 배송 처리중 -> 주문 취소
                .withExternal()
                .source(DeliveryStatus.SHIP_PROCESS)
                .target(DeliveryStatus.CANCEL)
                .event(DeliveryEvents.CancelOrder)
                .guard(guard.is())
                .action(action.action(DeliveryEvents.CancelOrder))
                .and();

    }
}
