package com.yjw.sprint.tech.statemachine.action.impl;

import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.statemachine.action.DeliveryAction;
import com.yjw.sprint.tech.statemachine.event.DeliveryEvents;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeliveryActionImpl implements DeliveryAction {

    // 5
    @Override
    public Action<DeliveryStatus, DeliveryEvents> action(DeliveryEvents event) {
        return ctx -> log.error("Delivery Action: {}", event);
    }
}
