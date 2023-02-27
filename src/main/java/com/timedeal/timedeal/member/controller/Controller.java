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
    public ResponseEntity<?> signUp(SignUpRequestDto sign) {
        log.info("memberId = " + sign.getMemberId());
        return memberWriteService.signUp(sign);
    }

    @GetMapping("")
    public ResponseEntity<?> getMember(@RequestParam String memberId) {
        return memberReadService.getMember(memberId);
    }
}
