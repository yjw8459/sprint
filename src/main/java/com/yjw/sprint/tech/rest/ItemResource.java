package com.yjw.sprint.tech.rest;

import com.yjw.sprint.tech.dto.ItemDTO;
import com.yjw.sprint.tech.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ItemResource {

    private ItemService itemService;

    public ItemResource(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/items")
    public ResponseEntity<ItemDTO> create(@RequestBody ItemDTO item){
        ItemDTO itemDTO = itemService.create(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }


}
