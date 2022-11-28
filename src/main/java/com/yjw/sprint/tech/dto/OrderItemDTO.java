package com.yjw.sprint.tech.dto;

import com.yjw.sprint.tech.entity.Order;
import com.yjw.sprint.tech.entity.OrderItem;
import lombok.Data;

@Data
public class OrderItemDTO {

    public Long id;

    public OrderDTO order;

    public ItemDTO item;

    public Long count;

    public OrderItemDTO id(Long id){
        this.id = id;
        return this;
    }

    public OrderItemDTO order(OrderDTO order){
        this.order = order;
        return this;
    }

    public OrderItemDTO item(ItemDTO item){
        this.item = item;
        return this;
    }

    public OrderItemDTO count(Long count){
        this.count = count;
        return this;
    }

    public OrderItem toEntity(){
        return new OrderItem().id(this.id)
                .item(this.item.toEntity())
                .count(this.count);
    }
}
