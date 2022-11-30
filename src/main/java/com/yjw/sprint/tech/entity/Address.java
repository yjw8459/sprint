package com.yjw.sprint.tech.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yjw.sprint.tech.dto.AddressDTO;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Address extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String address;

    @Column
    public String zipCode;

    @ManyToOne(fetch = FetchType.LAZY)
    public Member member;

    public Address id(Long id){
        this.id = id;
        return this;
    }

    public Address address(String address){
        this.address = address;
        return this;
    }

    public Address zipCode(String zipCode){
        this.zipCode = zipCode;
        return this;
    }

    public AddressDTO toDto(){
        return new AddressDTO().id(this.id)
                .address(this.address)
                .zipCode(this.zipCode);
    }

}
