package com.timedeal.timedeal.member.controller;

import com.timedeal.timedeal.member.dto.request.LoginRequestDto;
import com.timedeal.timedeal.member.dto.request.SignUpRequestDto;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        log.info(String.format("회원 등록 요청 : memberId = %s",signUpRequestDto.getMemberId()));
        return memberService.signUp(signUpRequestDto);
    }

    @GetMapping("")
    public ResponseEntity<?> getMember(@RequestParam String memberId) {
        log.info(String.format("회원 조회 요청: memberId = %s", memberId));
        return memberService.getMember(memberId);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteMember(@RequestParam String memberId) {
        log.info(String.format("회원 탈퇴 요청: memberId = %s", memberId));
        return memberService.deleteMember(memberId);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody LoginRequestDto loginRequestDto) {
        log.info(String.format("로그인 시도 : memberId = %s", loginRequestDto.getMemberId()));
        return memberService.login(request, loginRequestDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        log.info(String.format("로그아웃"));
        return ResponseEntity.success("로그아웃 되었습니다.");
    }
}
