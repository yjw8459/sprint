package com.yjw.sprint.tech.service;

import com.yjw.sprint.tech.dto.AddressDTO;
import com.yjw.sprint.tech.entity.Address;
import com.yjw.sprint.tech.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    public AddressRepository addressRepository;

    public List<AddressDTO> findAll(){
        return addressRepository.findAll().stream().map(address -> address.toDto()).collect(Collectors.toList());
    }

}
