package com.timedeal.timedeal.member.entity;

import com.timedeal.timedeal.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    Long id;
    String memberId;
    String email;
    String password;
    Role role;

    @OneToMany(fetch = FetchType.LAZY)
    List<Item> itemList = new ArrayList<>();
}
