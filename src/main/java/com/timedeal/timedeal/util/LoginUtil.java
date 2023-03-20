package com.timedeal.timedeal.util;

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

    public void checkLogin(Cookie cookie) {
        if (cookie == null) {
            log.info(String.format("로그인이 필요합니다."));
            throw  new IllegalArgumentException();
        }
    }

    public void checkMember(String memberId) {

    }
    public Optional<Member> login(HttpServletRequest request, Cookie cookie) {
        checkLogin(cookie);
        HttpSession session = request.getSession(false);
        MemberResponseDto findMember = (MemberResponseDto) session.getAttribute(cookie.getValue());
        Optional<Member> member = memberRepository.findByMemberId(findMember.getMemberId());

        if (member.get().getRole() == Role.USER) {
            log.info(String.format("상품 권한이 없습니다. 회원 권한 = %s", member.get().getRole()));
            throw  new IllegalArgumentException();
        }
        return member;
    }
}

