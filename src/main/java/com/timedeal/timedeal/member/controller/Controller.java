package com.timedeal.timedeal.member.controller;

import com.timedeal.timedeal.member.dto.request.SignUpRequestDto;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class Controller {

    private final MemberWriteService memberWriteService;

    @PostMapping("")
    public ResponseEntity<?> SignUp(SignUpRequestDto sign) {
        log.info("memberId = " + sign.getMemberId());
        return memberWriteService.SignUp(sign);
    }
}
