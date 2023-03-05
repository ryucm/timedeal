package com.timedeal.timedeal.item.entity;

import com.timedeal.timedeal.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    Long id;
    String itemName;
    int price;
    int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    Member member;
}
