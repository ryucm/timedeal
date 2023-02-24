package com.timedeal.timedeal.member.repository;

import com.timedeal.timedeal.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByMemberId(String memberId);
}
