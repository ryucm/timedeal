package com.timedeal.timedeal.member.controller;

import com.timedeal.timedeal.member.dto.request.SignUpRequestDto;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.service.MemberReadService;
import com.timedeal.timedeal.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class Controller {

    private final MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;

    @PostMapping("")
    public ResponseEntity<?> signUp(SignUpRequestDto signUpRequestDto) {
        log.info("회원 정보 저장: memberId = " + signUpRequestDto.getMemberId());
        return memberWriteService.signUp(signUpRequestDto);
    }

    @GetMapping("")
    public ResponseEntity<?> getMember(@RequestParam String memberId) {
        log.info("회원 정보 조회: memberId = " + memberId);
        return memberReadService.getMember(memberId);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteMember(@RequestParam String memberId) {
        log.info("회원 정보 삭제: memberId = " + memberId);
        return memberWriteService.deleteMember(memberId);
    }
}
