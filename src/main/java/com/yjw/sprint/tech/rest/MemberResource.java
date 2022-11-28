package com.yjw.sprint.tech.rest;

import com.yjw.sprint.tech.dto.MemberDTO;
import com.yjw.sprint.tech.entity.Member;
import com.yjw.sprint.tech.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemberResource {

    private MemberService memberService;

    public MemberResource(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 생성
    @PostMapping("/members")
    public ResponseEntity<MemberDTO> create(@RequestBody MemberDTO member){
        memberService.create(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    // 회원 단건조회
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable Long id){
        return ResponseEntity.ok(memberService.findOne(id));
    }

    // 회원 다건조회
    @GetMapping("/members")
    public ResponseEntity<Page<MemberDTO>> getMembers(Pageable pageable,
                                                      @RequestBody(required = false) Map<String, Object> searchKey){
        return ResponseEntity.ok(memberService.findAllWithFilter(searchKey, pageable));
    }

}
