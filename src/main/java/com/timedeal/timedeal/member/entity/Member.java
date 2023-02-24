package com.timedeal.timedeal.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
public class Member {

    @Id
    @GeneratedValue
    Long id;
    String memberId;
    String email;
    String password;
    Role role;
}
