package com.timedeal.timedeal.member.service;

import com.timedeal.timedeal.exception.ErrorCode;
import com.timedeal.timedeal.exception.Exceptions;
import com.timedeal.timedeal.member.dto.request.LoginRequestDto;
import com.timedeal.timedeal.member.dto.request.SignUpRequestDto;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.repository.MemberRepository;
import com.timedeal.timedeal.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LoginUtil loginUtil;

    public ResponseEntity<?> signUp(SignUpRequestDto signUpRequestDto) {
        loginUtil.validatorMember(signUpRequestDto.getMemberId());
        Member member = Member.builder()
                .memberId(signUpRequestDto.getMemberId())
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .role(signUpRequestDto.getRole())
                .build();
        Member savedMember = memberRepository.save(member);
        log.info(String.format("회원 등록 완료: memberId = %s", savedMember.getMemberId()));
        return ResponseEntity.success(member,"success");
    }

    @Transactional
    public ResponseEntity<?> deleteMember(String memberId) {
        loginUtil.validatorMember(memberId);
        memberRepository.deleteByMemberId(memberId);
        log.info(String.format("회원 삭제 완료: memberId = %s", memberId));
        return ResponseEntity.success("success");
    }

    public ResponseEntity<?> getMember(String memberId) {
        Member member = memberRepository.findByMemberId(memberId)
                .orElseThrow(()-> new Exceptions(ErrorCode.NOT_FOUND_MEMBER));
        return ResponseEntity.success(member, "success");
    }

    public ResponseEntity<?> login(HttpServletRequest request, LoginRequestDto loginRequestDto) {
        loginUtil.validatorMember(loginRequestDto.getMemberId());

        Member member = memberRepository.findByMemberId(loginRequestDto.getMemberId())
                .orElseThrow(()->new Exceptions(ErrorCode.NOT_FOUND_MEMBER));

        HttpSession session = request.getSession();
        String loginMember = UUID.randomUUID().toString();
        session.setAttribute(loginMember, member);

        log.info(String.format("로그인 정보 확인 : memberId = %s", member.getMemberId()));
        return ResponseEntity.success("로그인 성공");
    }


}
