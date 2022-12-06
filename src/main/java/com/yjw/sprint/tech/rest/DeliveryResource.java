package com.yjw.sprint.tech.rest;

import com.yjw.sprint.tech.dto.OrderDTO;
import com.yjw.sprint.tech.service.DeliveryService;
import com.yjw.sprint.tech.service.OrderService;
import com.yjw.sprint.tech.statemachine.event.DeliveryEvents;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeliveryResource {

    private DeliveryService deliveryService;

    public DeliveryResource(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PutMapping("/delivery/wait/{id}")
    public ResponseEntity<Void> wait(@PathVariable("id") Long orderId) {
        deliveryService.changeState(orderId, DeliveryEvents.StartDelivery);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @PutMapping("/delivery/process/{id}")
    public ResponseEntity<Void> process(@PathVariable("id") Long orderId) {
        deliveryService.changeState(orderId, DeliveryEvents.StartShipProcess);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @PutMapping("/delivery/ship/{id}")
    public ResponseEntity<Void> shipping(@PathVariable("id") Long orderId) {
        deliveryService.changeState(orderId, DeliveryEvents.StartShipping);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @PutMapping("/delivery/complete/{id}")
    public ResponseEntity<Void> complete(@PathVariable("id") Long orderId) {
        deliveryService.changeState(orderId, DeliveryEvents.EndShipping);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

    @PutMapping("/delivery/cancel/{id}")
    public ResponseEntity<Void> cancel(@PathVariable("id") Long orderId) {
        deliveryService.changeState(orderId, DeliveryEvents.CancelOrder);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }

}
