package com.yjw.sprint.tech.rest;

import com.yjw.sprint.tech.dto.MemberDTO;
import com.yjw.sprint.tech.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MemberResource {

    private MemberService memberService;

    public MemberResource(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public ResponseEntity<MemberDTO> create(@RequestBody MemberDTO member){
        memberService.create(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable Long id){
        return ResponseEntity.ok(memberService.fineOne(id));
    }

}
