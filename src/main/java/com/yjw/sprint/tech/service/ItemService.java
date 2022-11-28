package com.yjw.sprint.tech.service;

import com.yjw.sprint.tech.dto.ItemDTO;
import com.yjw.sprint.tech.repository.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemDTO create(ItemDTO itemDTO){
        return itemRepository.save(itemDTO.toEntity()).toDto();
    }

}
