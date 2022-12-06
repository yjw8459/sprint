package com.yjw.sprint.tech.service;

import com.yjw.sprint.tech.statemachine.event.DeliveryEvents;
import com.yjw.sprint.tech.statemachine.service.DeliveryStateEventService;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {

    private DeliveryStateEventService stateService;

    public DeliveryService(DeliveryStateEventService stateService) {
        this.stateService = stateService;
    }

    public void changeState(Long orderId, DeliveryEvents events){
        stateService.changeState(orderId, events);
    }

}
