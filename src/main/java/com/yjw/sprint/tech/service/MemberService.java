package com.yjw.sprint.tech.service;

import com.yjw.sprint.tech.dto.MemberDTO;
import com.yjw.sprint.tech.entity.Member;
import com.yjw.sprint.tech.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    public MemberDTO create(MemberDTO member){
        Member result = memberRepository.save(member.toEntity());
        return result.toDto();
    }

    public MemberDTO fineOne(Long id) {
        return memberRepository.findById(id).orElse(new Member()).toDto();
    }
}
