package com.timedeal.timedeal.member.service;

import com.timedeal.timedeal.member.dto.request.SignUpRequestDto;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;

    public ResponseEntity<?> SignUp(SignUpRequestDto signUpRequestDto) {
        if (memberRepository.existsByMemberId(signUpRequestDto.getMemberId())) {
            return ResponseEntity.fail("이미 존재하는 회원 ID입니다.");
        }
        Member member = Member.builder()
                .memberId(signUpRequestDto.getMemberId())
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .role(signUpRequestDto.getRole())
                .build();
        memberRepository.save(member);
        return ResponseEntity.success(member,"success");
    }

}
