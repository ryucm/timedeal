package com.timedeal.timedeal.item.controller;

import com.timedeal.timedeal.item.dto.ItemDto;
import com.timedeal.timedeal.item.service.ItemService;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("")
    public ResponseEntity<?> createItem(
            @RequestBody ItemDto itemDto,
            @SessionAttribute(name = "loginMember") Member member) {
        log.info("상품 등록 요청");
        return itemService.createItem(itemDto, member);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteItem(
            @RequestParam Long id,
            @SessionAttribute(name = "loginMember") Member member) {
        log.info("상품 삭제 요청");
        return itemService.deleteItem(id, member);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getItemList(Pageable pageable) {
        return itemService.getItemList(pageable);
    }

    @GetMapping("")
    public ResponseEntity<?> getItem(@RequestParam Long id) {
        return itemService.getItem(id);
    }

    @PutMapping("")
    public ResponseEntity<?> updateItem(
            @RequestParam Long id,
            @RequestBody ItemDto itemDto,
            @SessionAttribute(name = "loginMember") Member member) {
        return itemService.updateItem(id, itemDto, member);
    }

}
