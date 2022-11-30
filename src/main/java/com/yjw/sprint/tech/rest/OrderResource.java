package com.yjw.sprint.tech.rest;

import com.yjw.sprint.tech.dto.OrderDTO;
import com.yjw.sprint.tech.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderResource {

    private OrderService orderService;

    public OrderResource(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> create(@RequestBody OrderDTO order){
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.create(order.getMemberId(), order.getItemId(), order.getCount()));
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<OrderDTO>> searchOrders(Pageable pageable){
        return ResponseEntity.ok().body(orderService.searchOrders(pageable));
    }

    @PutMapping("/orders")
    public ResponseEntity<OrderDTO> update(@RequestBody OrderDTO order){
        return ResponseEntity.ok().body(orderService.update(order));
    }

}
