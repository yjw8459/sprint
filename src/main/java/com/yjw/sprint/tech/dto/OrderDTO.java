package com.yjw.sprint.tech.dto;

import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.entity.Order;
import com.yjw.sprint.tech.entity.OrderItem;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class OrderDTO {

    public Long id;

    public List<OrderItemDTO> orderItems;

    public String orderer;

    public DeliveryStatus status;

    public OrderDTO id(Long id){
        this.id = id;
        return this;
    }

    public OrderDTO orderItems(Set<OrderItem> orderItems){
        this.orderItems = orderItems.stream().map(orderItem -> orderItem.toDto()).collect(Collectors.toList());
        return this;
    }

    public OrderDTO orderer(String orderer){
        this.orderer = orderer;
        return this;
    }

    public OrderDTO status(DeliveryStatus status){
        this.status = status;
        return this;
    }

    public Order toEntity(){
        return new Order().id(this.id)
                .orderItems(this.orderItems)
                .orderer(this.orderer)
                .status(this.status);
    }
}
