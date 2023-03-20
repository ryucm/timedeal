package com.timedeal.timedeal.order.entity;

import com.timedeal.timedeal.item.entity.Item;
import com.timedeal.timedeal.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item product;
}
