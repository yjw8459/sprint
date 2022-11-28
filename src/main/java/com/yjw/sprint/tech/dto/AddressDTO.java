package com.yjw.sprint.tech.dto;

import com.yjw.sprint.tech.entity.Address;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * DTO
 */

@Slf4j
@Data
public class AddressDTO {

    private Long id;

    private String address;

    private String zipCode;

    public AddressDTO id(Long id){
        this.id = id;
        return this;
    }

    public AddressDTO address(String address){
        this.address = address;
        return this;
    }

    public AddressDTO zipCode(String zipCode){
        this.zipCode = zipCode;
        return this;
    }

    public Address toEntity(){
        return new Address().id(this.id)
                .address(this.address)
                .zipCode(this.zipCode);
    }
}
