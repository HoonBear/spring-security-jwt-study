package com.example.springsecurity.dto;

import lombok.Data;

@Data
public class MemberJoinRequestDTO {
    private String memberId;
    private String password;
}
