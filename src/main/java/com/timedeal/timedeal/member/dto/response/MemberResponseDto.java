package com.timedeal.timedeal.member.dto.response;

import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.entity.Role;
import lombok.Getter;

import java.util.Optional;

@Getter
public class MemberResponseDto {
    Long id;
    String memberId;
    String email;
    Role role;

    public MemberResponseDto(Optional<Member> member) {
        this.id = member.get().getId();
        this.memberId = member.get().getMemberId();
        this.email = member.get().getEmail();
        this.role = member.get().getRole();
    }
}
