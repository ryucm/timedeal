package com.timedeal.timedeal.item.entity;

import com.timedeal.timedeal.item.dto.ItemDto;
import com.timedeal.timedeal.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private int price;
    private int stock;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member admin;

    public Item updateItem(ItemDto itemDto) {
        this.itemName = ObjectUtils.defaultIfNull(itemDto.getItemName(), this.itemName);
        this.price = ObjectUtils.defaultIfNull(itemDto.getPrice(), this.price);
        this.stock = ObjectUtils.defaultIfNull(itemDto.getStock(), this.stock);
        this.startTime = ObjectUtils.defaultIfNull(itemDto.getStartTime(), this.startTime);
        this.endTime = ObjectUtils.defaultIfNull(itemDto.getEndTime(), this.endTime);
        return this;
    }
}
