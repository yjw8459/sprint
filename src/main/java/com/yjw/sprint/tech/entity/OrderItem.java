package com.yjw.sprint.tech.entity;

import com.yjw.sprint.tech.dto.ItemDTO;
import com.yjw.sprint.tech.dto.OrderDTO;
import com.yjw.sprint.tech.dto.OrderItemDTO;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    public Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    public Item item;

    @Column
    public Long count;

    public OrderItem id(Long id){
        this.id = id;
        return this;
    }

    public OrderItem order(Order order){
        this.order = order;
        return this;
    }

    public OrderItem item(Item item){
        this.item = item;
        return this;
    }

    public OrderItem count(Long count){
        this.count = count;
        return this;
    }

    public OrderItemDTO toDto(){
        return new OrderItemDTO().id(this.id)
                .item(this.item.toDto())
                .count(this.count);
    }

    public static OrderItem createOrderItem(Item item, int price, Long count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);

        item.setQuantity(item.getQuantity() - count);
        return orderItem;
    }

}
