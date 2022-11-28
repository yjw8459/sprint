package com.yjw.sprint.tech.service;

import com.yjw.sprint.tech.dto.MemberDTO;
import com.yjw.sprint.tech.entity.Member;
import com.yjw.sprint.tech.repository.MemberRepository;
import com.yjw.sprint.tech.repository.specification.MemberSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public MemberDTO create(MemberDTO memberDTO){
        Member member = memberDTO.toEntity();
        Member result = memberRepository.save(member);
        return result.toDto();
    }

    public MemberDTO findOne(Long id) {
        return memberRepository.findById(id).orElse(new Member()).toDto();
    }

    public List<MemberDTO> findAll(){
        return memberRepository.findAll().stream().map(member -> member.toDto()).collect(Collectors.toList());
    }
    public Page<MemberDTO> findAllWithFilter(Map<String, Object> searchKey, Pageable pageable) {
        Page<MemberDTO> page = memberRepository.findAll(MemberSpecification.searchMember(searchKey), pageable).map(member -> member.toDto());
        return page;
    }
}
