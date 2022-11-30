package com.yjw.sprint.tech.entity;

import com.yjw.sprint.tech.dto.AddressDTO;
import com.yjw.sprint.tech.dto.MemberDTO;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
public class Member extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column
    public String name;

    @Column
    public String email;

    // CascadeType.PERSIST: 저장 시 함께 저장
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    public Set<Address> address = new HashSet<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    public Set<Order> orders = new HashSet<>();


    public Member id(Long id){
        this.id = id;
        return this;
    }

    public Member name(String name){
        this.name = name;
        return this;
    }

    public Member email(String email){
        this.email = email;
        return this;
    }

    public Member address(List<AddressDTO> address){
        this.address = address.stream().map(AddressDTO::toEntity).collect(Collectors.toSet());
        return this;
    }

    public MemberDTO toDto(){
        return new MemberDTO()
                .id(this.id)
                .name(this.name)
                .email(this.email)
                .address(this.address);
    }
}
