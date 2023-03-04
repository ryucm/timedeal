package com.timedeal.timedeal.member.controller;

import com.timedeal.timedeal.member.dto.request.LoginRequestDto;
import com.timedeal.timedeal.member.dto.request.SignUpRequestDto;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class Controller {

    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        log.info("회원 정보 저장: memberId = " + signUpRequestDto.getMemberId());
        return memberService.signUp(signUpRequestDto);
    }

    @GetMapping("/member")
    public ResponseEntity<?> getMember(@RequestParam String memberId) {
        log.info("회원 정보 조회: memberId = " + memberId);
        return memberService.getMember(memberId);
    }

    @DeleteMapping("/member")
    public ResponseEntity<?> deleteMember(@RequestParam String memberId) {
        log.info("회원 정보 삭제: memberId = " + memberId);
        return memberService.deleteMember(memberId);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(HttpServletRequest request, @RequestBody LoginRequestDto loginRequestDto) {
        return memberService.login(request, loginRequestDto);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.success("로그아웃 되었습니다.");
    }
}
