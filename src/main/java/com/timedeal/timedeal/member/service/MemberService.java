package com.timedeal.timedeal.member.service;

import com.timedeal.timedeal.member.dto.request.LoginRequestDto;
import com.timedeal.timedeal.member.dto.request.SignUpRequestDto;
import com.timedeal.timedeal.member.dto.response.MemberResponseDto;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public ResponseEntity<?> signUp(SignUpRequestDto signUpRequestDto) {
        if (validatorMember(signUpRequestDto.getMemberId())) {
            return ResponseEntity.fail("이미 존재하는 회원 ID입니다.");
        }
        Member member = Member.builder()
                .memberId(signUpRequestDto.getMemberId())
                .email(signUpRequestDto.getEmail())
                .password(signUpRequestDto.getPassword())
                .role(signUpRequestDto.getRole())
                .build();
        memberRepository.save(member);
        log.info(String.format("회원 등록 완료: memberId = %s", signUpRequestDto.getMemberId()));
        return ResponseEntity.success(member,"success");
    }

    @Transactional
    public ResponseEntity<?> deleteMember(String memberId) {
        if (!validatorMember(memberId)) {
            return ResponseEntity.fail("존재하지 않는 회원 ID 입니다.");
        }
        memberRepository.deleteByMemberId(memberId);
        log.info(String.format("회원 삭제 완료: memberId = %s", memberId));
        return ResponseEntity.success("success");
    }

    public ResponseEntity<?> getMember(String memberId) {
        Optional<Member> member = memberRepository.findByMemberId(memberId);
        if (member.isPresent()) {
            log.info(String.format("회원 조회 완료: memberId = %s", memberId));
            return ResponseEntity.success(member.get(), "success");
        } else {
            log.info(String.format("존재하지 않는 회원: memberId = %s", memberId));
            return ResponseEntity.fail("회원 정보가 존재하지 않습니다.");
        }
    }

    public ResponseEntity<?> login(HttpServletRequest request, LoginRequestDto loginRequestDto) {
        if (!validatorMember(loginRequestDto.getMemberId())) {
            return ResponseEntity.fail("회원 정보가 없습니다.");
        }

        Optional<Member> member = memberRepository.findByMemberId(loginRequestDto.getMemberId());
        // 로그인 성공 처리
        // 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        // 세션에 로그인 회원 정보 보관
        session.setAttribute("LOGIN_MEMBER", new MemberResponseDto(member));
        log.info(String.format("로그인 정보 확인 : memberId = %s", member.get().getMemberId()));
        return ResponseEntity.success("로그인 성공");
    }

    private boolean validatorMember(String memberId) {
        if (!memberRepository.existsByMemberId(memberId)) {
            log.info(String.format("존재하지 않는 회원: memberId = %s", memberId));
            return false;
        }
        log.info(String.format("회원 정보 확인 : memberId = %s", memberId));
        return true;
    }
}
