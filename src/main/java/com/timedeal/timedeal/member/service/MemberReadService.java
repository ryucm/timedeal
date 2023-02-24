package com.timedeal.timedeal.member.service;

import com.timedeal.timedeal.member.dto.request.SignUpRequestDto;
import com.timedeal.timedeal.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReadService {
    private final MemberRepository memberRepository;

}
