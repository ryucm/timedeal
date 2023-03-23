package com.timedeal.timedeal.util;

import com.timedeal.timedeal.exception.ErrorCode;
import com.timedeal.timedeal.exception.Exceptions;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.entity.Role;
import com.timedeal.timedeal.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final MemberRepository memberRepository;

    public void validatorMember(String memberId) {
        if (!memberRepository.existsByMemberId(memberId)) {
            log.info(String.format("존재하지 않는 회원: memberId = %s", memberId));
            throw new Exceptions(ErrorCode.NOT_FOUND_MEMBER);
        }
        log.info(String.format("회원 정보 확인 : memberId = %s", memberId));
    }

    public void checkItemPermission(Member member) {
        log.info(String.format("Member 권한 : %s", member.getRole()));
        if (member.getRole() == Role.USER) {
            log.error(String.format("상품 권한이 없습니다. 회원 권한 = %s", member.getRole()));
            throw  new Exceptions(ErrorCode.INVALID_PERMISSION);
        }
    }
}

