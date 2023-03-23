package com.timedeal.timedeal.service;

import com.timedeal.timedeal.member.dto.request.SignUpRequestDto;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.entity.Role;
import com.timedeal.timedeal.member.repository.MemberRepository;
import com.timedeal.timedeal.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@SpringBootTest
@AutoConfigureMockMvc
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    private String MEMBER_ID = "memberId";

    @BeforeEach
    @DisplayName("회원가입")
    public void signUp() {
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto(MEMBER_ID, "email", "password", Role.ADMIN);
        memberService.signUp(signUpRequestDto);
        Optional<Member> member = memberRepository.findByMemberId(signUpRequestDto.getMemberId());

    }

    @Test
    @DisplayName("회원 정보 조회")
    public void getMember() {
        memberService.getMember(MEMBER_ID);
    }

    @Test
    @DisplayName("회원 탈퇴")
    public void deleteMember() {
        memberService.deleteMember(MEMBER_ID);
    }

    @Test
    @DisplayName("로그인")
    public void login() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

    }
}
