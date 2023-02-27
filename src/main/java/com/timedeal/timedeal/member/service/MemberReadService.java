package com.timedeal.timedeal.member.service;

import com.timedeal.timedeal.member.dto.request.SignUpRequestDto;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberReadService {
    private final MemberRepository memberRepository;

    public ResponseEntity<?> getMember(String memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        if (member.isPresent()) {
            return ResponseEntity.success(member.get(), "success");
        } else {
            return ResponseEntity.fail("회원 정보가 존재하지 않습니다.");
        }
    }
}
