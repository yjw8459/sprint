package com.yjw.sprint.tech.rest;

import com.yjw.sprint.tech.dto.OrderDTO;
import com.yjw.sprint.tech.service.DeliveryService;
import com.yjw.sprint.tech.service.OrderService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DeliveryResource {

    private DeliveryService deliveryService;

    public OrderDTO delivery(){
        return null;
    }
}
