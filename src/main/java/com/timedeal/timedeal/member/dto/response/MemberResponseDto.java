package com.timedeal.timedeal.member.dto.response;

import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.entity.Role;
import lombok.Getter;

import java.util.Optional;

@Getter
public class MemberResponseDto {
    private Long id;
    private String memberId;
    private String email;
    private Role role;

    public MemberResponseDto(Optional<Member> member) {
        this.id = member.get().getId();
        this.memberId = member.get().getMemberId();
        this.email = member.get().getEmail();
        this.role = member.get().getRole();
    }
}
