package com.yjw.sprint.tech.entity;

import com.yjw.sprint.tech.dto.ItemDTO;
import com.yjw.sprint.tech.dto.OrderItemDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String name;

    @Column
    public Long quantity;

    public Item id(Long id){
        this.id = id;
        return this;
    }

    public Item name(String name){
        this.name = name;
        return this;
    }

    public Item quantity(Long quantity){
        this.quantity = quantity;
        return this;
    }

    public void addQuantity(Long quantity){
        this.quantity += quantity;
    }

    public ItemDTO toDto(){
        return new ItemDTO().id(this.id)
                .name(this.name)
                .quantity(this.quantity);
    }

}
