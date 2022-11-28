package com.yjw.sprint.tech.service;

import com.yjw.sprint.tech.dto.OrderDTO;
import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.entity.Order;
import com.yjw.sprint.tech.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderService {

    private OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO create(OrderDTO orderDto){
        Order order = orderDto.toEntity();
        order.setStatus(DeliveryStatus.ORDER);
        order = orderRepository.save(order);
        return order.toDto();
    }

}
