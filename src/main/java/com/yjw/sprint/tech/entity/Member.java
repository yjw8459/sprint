package com.yjw.sprint.tech.entity;

import com.yjw.sprint.tech.dto.MemberDTO;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

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
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    public Address address;


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

    public Member address(Address address){
        this.address = address;
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
