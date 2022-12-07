package com.yjw.sprint.tech.statemachine;

import org.springframework.statemachine.StateMachineContext;
import java.io.Serializable;
public interface ContextEntity<S, E, ID extends Serializable> /*extends Identifiable<ID>*/ {

    StateMachineContext<S, E> getStateMachineContext();

    void setStateMachineContext(StateMachineContext<S, E> context);

}

