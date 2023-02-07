package com.example.springsecurity.service;

import com.example.springsecurity.dto.MemberJoinRequestDTO;
import com.example.springsecurity.dto.MemberLoginRequestDTO;
import com.example.springsecurity.jwt.TokenDTO;
import com.example.springsecurity.jwt.TokenProvider;
import com.example.springsecurity.model.Member;
import com.example.springsecurity.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void join(MemberJoinRequestDTO request) {
        memberRepository.save(
            Member.builder()
                  .memberId(request.getMemberId())
                  .password(passwordEncoder.encode(request.getPassword()))
                  .roles(List.of("USER"))
                  .build()
        );
    }

    @Transactional
    public TokenDTO login(MemberLoginRequestDTO request) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getMemberId(), request.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return tokenProvider.generateTokenDTO(authentication);
    }
}
