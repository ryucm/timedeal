package com.timedeal.timedeal.member.dto.request;

import com.timedeal.timedeal.member.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {
    String memberId;
    String email;
    String password;
    Role role;
}
