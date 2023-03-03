package com.timedeal.timedeal.member.service;

import com.timedeal.timedeal.member.dto.request.SignUpRequestDto;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberWriteService {

    private final MemberRepository memberRepository;

    public ResponseEntity<?> signUp(SignUpRequestDto signUpRequestDto) {
        if (memberRepository.existsByMemberId(signUpRequestDto.getMemberId())) {
            log.info("이미 존재하는 회원: memberId = " + signUpRequestDto.getMemberId());
            return ResponseEntity.fail("이미 존재하는 회원 ID입니다.");
        }
        Member member = Member.builder()
                .memberId(signUpRequestDto.getMemberId())
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .role(signUpRequestDto.getRole())
                .build();
        memberRepository.save(member);
        log.info("회원 등록 완료: memberId = " + signUpRequestDto.getMemberId());
        return ResponseEntity.success(member,"success");
    }

    @Transactional
    public ResponseEntity<?> deleteMember(String memberId) {
        if (!memberRepository.existsByMemberId(memberId)) {
            log.info("존재하지 않는 회원: memberId = " + memberId);
            return ResponseEntity.fail("존재하지 않는 회원 ID 입니다.");
        }
        memberRepository.deleteByMemberId(memberId);
        log.info("회원 삭제 완료: memberId = " + memberId);
        return ResponseEntity.success("success");
    }
}
