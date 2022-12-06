package com.yjw.sprint.tech.statemachine.action;

import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.statemachine.event.DeliveryEvents;
import org.springframework.statemachine.action.Action;

public interface DeliveryAction {

    public Action<DeliveryStatus, DeliveryEvents> action(DeliveryEvents event);

}
