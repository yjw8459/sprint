package com.yjw.sprint.tech.statemachine.guard;

import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.statemachine.event.DeliveryEvents;
import org.springframework.statemachine.guard.Guard;

public interface DeliveryGuard {

    public Guard<DeliveryStatus, DeliveryEvents> is();
}
