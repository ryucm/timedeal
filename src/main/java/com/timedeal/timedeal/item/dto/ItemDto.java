package com.timedeal.timedeal.item.dto;

import com.timedeal.timedeal.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ItemDto {
    String itemName;
    int price;
    int stock;
    LocalDateTime startTime;
    LocalDateTime endTime;

    public ItemDto(Item item) {
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.stock = item.getStock();
    }
}
