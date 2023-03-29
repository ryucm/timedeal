package com.timedeal.timedeal.item.dto;

import com.timedeal.timedeal.item.entity.Item;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    @NotNull
    String itemName;

    @NotNull
    int price;

    @NotNull
    int stock;

    @NotNull
    LocalDateTime startTime;

    @NotNull
    LocalDateTime endTime;

    public ItemDto(Item item) {
        this.itemName = item.getItemName();
        this.price = item.getPrice();
        this.stock = item.getStock();
    }
}
