package com.yjw.sprint.tech.entity;


import com.yjw.sprint.tech.dto.OrderDTO;
import com.yjw.sprint.tech.dto.OrderItemDTO;
import com.yjw.sprint.tech.dto.enumerate.DeliveryStatus;
import com.yjw.sprint.tech.dto.enumerate.OrderStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    public Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    public Set<OrderItem> orderItems = new HashSet<>();

    @Column
    public String orderer;

    @Column
    @Enumerated(EnumType.STRING)
    public DeliveryStatus deliveryStatus;

    @Column
    @Enumerated(EnumType.STRING)
    public OrderStatus orderStatus;

    public Order id(Long id){
        this.id = id;
        return this;
    }

    public Order orderItems(List<OrderItemDTO> orderItems){
        this.orderItems = orderItems.stream().map(orderItem -> orderItem.toEntity()).collect(Collectors.toSet());
        return this;
    }

    public Order orderer(String orderer){
        this.orderer = orderer;
        return this;
    }

    public Order deliveryStatus(DeliveryStatus status){
        this.deliveryStatus = status;
        return this;
    }

    public Order orderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderDTO toDto(){
        return new OrderDTO().id(this.id)
                .orderItems(this.orderItems)
                .orderer(this.orderer)
                .deliveryStatus(this.deliveryStatus)
                .orderStatus(this.orderStatus);
    }

    public static Order createOrder(Member member, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        for (OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.toString();
        return order;
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public Order cancelOrder(Order order){
        order.setOrderStatus(OrderStatus.CANCEL);
        return order;
    }


}
