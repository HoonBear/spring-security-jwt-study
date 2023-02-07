package com.example.springsecurity.controller;

import com.example.springsecurity.dto.MemberJoinRequestDTO;
import com.example.springsecurity.dto.MemberLoginRequestDTO;
import com.example.springsecurity.jwt.TokenDTO;
import com.example.springsecurity.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberJoinRequestDTO request) {
        memberService.join(request);
        return ResponseEntity.status(201).body(null);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequestDTO request) {
        return ResponseEntity.status(200).body(memberService.login(request));
    }

    @GetMapping("/test")
    public String test() {
        return "pass";
    }
}