package com.yjw.sprint.tech.dto;

import com.yjw.sprint.tech.entity.Address;
import com.yjw.sprint.tech.entity.Member;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * DTO
 */
@Slf4j
@Data
public class MemberDTO {

    private Long id;

    private String name;

    private String email;

    private AddressDTO address;

    public MemberDTO id(Long id){
        this.id = id;
        return this;
    }

    public MemberDTO name(String name){
        this.name = name;
        return this;
    }

    public MemberDTO email(String email){
        this.email = email;
        return this;
    }

    public MemberDTO address(Address address){
        this.address = address.toDto();
        return this;
    }


    public Member toEntity(){
        return new Member().id(this.id)
                .name(this.name)
                .email(this.email)
                .address(this.address.toEntity());
    }

}
