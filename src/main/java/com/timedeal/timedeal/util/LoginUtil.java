package com.timedeal.timedeal.util;

import com.timedeal.timedeal.exception.ErrorCode;
import com.timedeal.timedeal.exception.Exceptions;
import com.timedeal.timedeal.member.dto.response.MemberResponseDto;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.entity.Role;
import com.timedeal.timedeal.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginUtil {

    private final MemberRepository memberRepository;

    public void validatorMember(String memberId) {
        if (!memberRepository.existsByMemberId(memberId)) {
            log.info(String.format("존재하지 않는 회원: memberId = %s", memberId));
            throw new Exceptions(ErrorCode.NOT_FOUND_MEMBER);
        }
        log.info(String.format("회원 정보 확인 : memberId = %s", memberId));
    }

    public void checkPermission(Optional<Member> member) {
        if (member.get().getRole() == Role.USER) {
            log.info(String.format("상품 권한이 없습니다. 회원 권한 = %s", member.get().getRole()));
            throw  new Exceptions(ErrorCode.INVALID_PERMISSION);
        }
    }
    public Optional<Member> login(HttpServletRequest request, Cookie cookie) {
        HttpSession session = request.getSession(false);
        MemberResponseDto findMember = (MemberResponseDto) session.getAttribute(cookie.getValue());
        Optional<Member> member = memberRepository.findByMemberId(findMember.getMemberId());
        checkPermission(member);
        return member;
    }
}

