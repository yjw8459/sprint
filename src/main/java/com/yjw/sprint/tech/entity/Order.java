package com.yjw.sprint.tech.entity;


import com.yjw.sprint.tech.dto.OrderDTO;
import com.yjw.sprint.tech.dto.OrderItemDTO;
import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@Data
public class Order extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    public Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    public Set<OrderItem> orderItems = new HashSet<>();

    @Column
    public String orderer;

    @Column
    @Enumerated(EnumType.STRING)
    public DeliveryStatus status;

    public Order id(Long id){
        this.id = id;
        return this;
    }

    public Order orderItems(List<OrderItemDTO> orderItems){
        System.out.println("==============================");
        System.out.println(orderItems.toString());
        System.out.println("==============================");
        this.orderItems = orderItems.stream().map(orderItem -> orderItem.toEntity()).collect(Collectors.toSet());
        System.out.println("==============================");
        System.out.println(this.orderItems.toString());
        System.out.println("==============================");
        return this;
    }

    public Order orderer(String orderer){
        this.orderer = orderer;
        return this;
    }

    public Order status(DeliveryStatus status){
        this.status = status;
        return this;
    }

    public OrderDTO toDto(){
        return new OrderDTO().id(this.id)
                .orderItems(this.orderItems)
                .orderer(this.orderer)
                .status(this.status);
    }
}