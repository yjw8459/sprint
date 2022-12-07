package com.yjw.sprint.tech.statemachine;

import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.statemachine.action.DeliveryAction;
import com.yjw.sprint.tech.statemachine.event.DeliveryEvents;
import com.yjw.sprint.tech.statemachine.guard.DeliveryGuard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

@Configuration
@EnableStateMachineFactory(name = "deliveryStateMachineFactory")
@Slf4j
public class DeliveryStateMachineConfigure extends StateMachineConfigurerAdapter<DeliveryStatus, DeliveryEvents> {

    private DeliveryGuard guard;

    private DeliveryAction action;

    public DeliveryStateMachineConfigure(DeliveryGuard guard, DeliveryAction action) {
        this.guard = guard;
        this.action = action;
    }

    // State Machine Config
    @Override
    public void configure(StateMachineConfigurationConfigurer<DeliveryStatus, DeliveryEvents> config) throws Exception {
        config.withConfiguration()
                .listener(loggingListener());
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

//    @Bean
//    public StateMachineAdapter<DeliveryStatus, DeliveryEvents, ContextEntity<DeliveryStatus, DeliveryEvents, Serializable>> memberStateMachineAdapter(
//            StateMachineFactory<DeliveryStatus, DeliveryEvents> stateMachineFactory,
//            StateMachinePersister<DeliveryStatus, DeliveryEvents, ContextEntity<DeliveryStatus, DeliveryEvents, Serializable>> persister) {
//        return new StateMachineAdapter<>(stateMachineFactory, persister);
//    }

    @Bean
    public StateMachinePersister<DeliveryStatus, DeliveryEvents, ContextEntity<DeliveryStatus, DeliveryEvents, Serializable>> deliveryStateMachinePersister(
            StateMachinePersist<DeliveryStatus, DeliveryEvents, ContextEntity<DeliveryStatus, DeliveryEvents, Serializable>> persist) {
        return new DefaultStateMachinePersister<>(persist);
    }

    /**
     * 메모리 내 지속 상태 머신
     * @return
     */
    @Bean
    public StateMachinePersist<DeliveryStatus, DeliveryEvents, ContextEntity<DeliveryStatus, DeliveryEvents, Serializable>> deliveryStateMachinePersist(){
       return new StateMachinePersist<DeliveryStatus, DeliveryEvents, ContextEntity<DeliveryStatus, DeliveryEvents, Serializable>>() {

           // 간단 스토리지(상태 시스템 저장), 상태 머신 인스턴스가 아닌, 상태 머신 컨텍스트 저장
           private HashMap<UUID, StateMachineContext<DeliveryStatus, DeliveryEvents>> storage = new HashMap<>();
           @Override
           public void write(StateMachineContext<DeliveryStatus, DeliveryEvents> context,
                             ContextEntity<DeliveryStatus, DeliveryEvents, Serializable> contextObj) throws Exception {
               log.error("StateMachinePersist write() --->");
               log.error("StateMachinePersist write() --->");
               log.error("StateMachinePersist write() --->");
               contextObj.setStateMachineContext(context);
           }

           @Override
           public StateMachineContext<DeliveryStatus, DeliveryEvents> read(ContextEntity<DeliveryStatus, DeliveryEvents, Serializable> contextObj) throws Exception {
               log.error("StateMachinePersist read() --->");
               log.error("StateMachinePersist read() --->");
               log.error("StateMachinePersist read() --->");
               return contextObj.getStateMachineContext();
           }
       };
    }

    public StateMachineListener<DeliveryStatus, DeliveryEvents> loggingListener() {
        return new StateMachineListenerAdapter<DeliveryStatus, DeliveryEvents>() {
            @Override
            public void stateChanged(State<DeliveryStatus, DeliveryEvents> from, State<DeliveryStatus, DeliveryEvents> to) {
                log.error("State changed: {} -> {}",from.getId(),to.getId());
            }

            @Override
            public void eventNotAccepted(Message<DeliveryEvents> message) {
                log.error("Event not accepted: {}", message/* .getPayload() */ );
            }

            @Override
            public void stateEntered(State<DeliveryStatus, DeliveryEvents> state) {
                log.error("State entered to {}", state.getId());
            }

            @Override
            public void stateExited(State<DeliveryStatus, DeliveryEvents> state) {
                log.error("State exited from {}", state.getId());
            }

            @Override
            public void transition(Transition<DeliveryStatus, DeliveryEvents> transition) {
                log.error("State transition happened to {}", transition.getTarget());
            }

            @Override
            public void transitionStarted(Transition<DeliveryStatus, DeliveryEvents> transition) {
                log.error("State transition started to {}", transition.getTarget());
            }

            @Override
            public void transitionEnded(Transition<DeliveryStatus, DeliveryEvents> transition) {
                log.error("State transition ended to {}", transition.getTarget());
            }

            @Override
            public void stateMachineStarted(StateMachine<DeliveryStatus, DeliveryEvents> stateMachine) {
                log.error("StateMachine started {}", stateMachine.getId());
            }

            @Override
            public void stateMachineStopped(StateMachine<DeliveryStatus, DeliveryEvents> stateMachine) {
                log.error("StateMachine stopped {}", stateMachine.getId());
            }

            @Override
            public void extendedStateChanged(Object key, Object value) {
                log.error("Extended State changed to <{},{}>", key, value);
            }

            @Override
            public void stateMachineError(StateMachine<DeliveryStatus, DeliveryEvents> stateMachine, Exception exception) {
                log.error("StateMachine error {}", stateMachine.getId());
            }
        };
    }
}
