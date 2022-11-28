package com.yjw.sprint.tech.dto;

import com.yjw.sprint.tech.entity.Item;
import com.yjw.sprint.tech.entity.OrderItem;
import lombok.Data;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class ItemDTO {

    public Long id;

    public String name;

    public Long quantity;

    public ItemDTO id(Long id){
        this.id = id;
        return this;
    }

    public ItemDTO name(String name){
        this.name = name;
        return this;
    }


    public ItemDTO quantity(Long quantity){
        this.quantity = quantity;
        return this;
    }


    public Item toEntity(){
        return new Item().id(this.id)
                .name(this.name)
                .quantity(this.quantity);
    }

}
