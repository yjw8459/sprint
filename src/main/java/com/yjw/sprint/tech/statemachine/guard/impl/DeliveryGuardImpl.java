package com.yjw.sprint.tech.statemachine.guard.impl;

import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.statemachine.event.DeliveryEvents;
import com.yjw.sprint.tech.statemachine.guard.DeliveryGuard;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Service;

@Configuration
public class DeliveryGuardImpl implements DeliveryGuard {

    @Override
    public Guard<DeliveryStatus, DeliveryEvents> is() {
        return ctx -> Boolean.TRUE;
    }
}
