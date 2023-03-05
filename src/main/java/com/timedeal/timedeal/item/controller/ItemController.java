package com.timedeal.timedeal.item.controller;

import com.timedeal.timedeal.item.dto.ItemDto;
import com.timedeal.timedeal.item.service.ItemService;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping("")
    public ResponseEntity<?> createItem(
            @RequestBody ItemDto itemDto,
            HttpServletRequest request,
            @CookieValue(name = "uuid", required = false) Cookie cookie) {
        log.info(String.format("상품 등록"));
        return itemService.createItem(itemDto, request, cookie);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        log.info(String.format("상품 삭제"));
        return itemService.deleteItem(id);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getItems() {
        return itemService.getItems();
    }

    @GetMapping("")
    public ResponseEntity<?> getItem(@PathVariable Long id) {
        return itemService.getItem(id);
    }

    @PutMapping("")
    public ResponseEntity<?> updateItem(
            @RequestBody ItemDto itemDto,
            HttpServletRequest request) {
        return itemService.updateItem(itemDto, request);
    }
}
