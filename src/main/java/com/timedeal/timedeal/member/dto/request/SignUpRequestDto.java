package com.timedeal.timedeal.member.dto.request;

import com.timedeal.timedeal.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequestDto {
    String memberId;
    String email;
    String password;
    Role role;
}
