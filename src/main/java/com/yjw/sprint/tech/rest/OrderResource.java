package com.yjw.sprint.tech.rest;

import com.yjw.sprint.tech.dto.OrderDTO;
import com.yjw.sprint.tech.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderResource {

    private OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO order){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(order));
    }

}
