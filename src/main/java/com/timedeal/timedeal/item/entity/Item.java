package com.timedeal.timedeal.item.entity;

import com.timedeal.timedeal.exception.ErrorCode;
import com.timedeal.timedeal.exception.Exceptions;
import com.timedeal.timedeal.item.dto.ItemDto;
import com.timedeal.timedeal.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Version
    private Long version;

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

    public void checkSoldOut(int purchaseNum) {
        if (this.getStock() - purchaseNum < 0) {
            throw new Exceptions(ErrorCode.SOLD_OUT);
        } else {
            this.stock -= purchaseNum;
        }
    }

    public void checkSaleTime() {
        if (!LocalDateTime.now().isAfter(this.getStartTime()) && LocalDateTime.now().isBefore(this.getEndTime())) {
            throw new Exceptions(ErrorCode.NOT_SALE_TIME);
        }
    }
}
