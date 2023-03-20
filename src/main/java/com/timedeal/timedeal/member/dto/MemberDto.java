package com.timedeal.timedeal.member.dto;

import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {
    Long id;
    String memberId;
    String email;

    public MemberDto(Member member) {
        this.id = member.getId();
        this.memberId = member.getMemberId();
        this.email = member.getEmail();
    }

}
