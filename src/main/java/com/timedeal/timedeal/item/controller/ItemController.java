package com.timedeal.timedeal.item.controller;

import com.timedeal.timedeal.item.dto.ItemDto;
import com.timedeal.timedeal.item.service.ItemService;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final LoginUtil loginUtil;

    @PostMapping("")
    public ResponseEntity<?> createItem(
            @RequestBody ItemDto itemDto,
            HttpServletRequest request,
            @CookieValue(name = "uuid", required = false) Cookie cookie) {
        log.info(String.format("상품 등록 요청"));

        Optional<Member> loginMember = loginUtil.login(request, cookie);
        return itemService.createItem(itemDto, loginMember);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteItem(
            @RequestParam Long id,
            HttpServletRequest request,
            @CookieValue(name = "uuid", required = false) Cookie cookie) {
        log.info(String.format("상품 삭제 요청"));
        return itemService.deleteItem(id, request, cookie);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getItemList() {
        return itemService.getItemList();
    }

    @GetMapping("")
    public ResponseEntity<?> getItem(@RequestParam Long id) {
        return itemService.getItem(id);
    }

    @PutMapping("")
    public ResponseEntity<?> updateItem(
            @RequestParam Long id,
            @RequestBody ItemDto itemDto,
            HttpServletRequest request,
            @CookieValue(name = "uuid", required = false) Cookie cookie) {
        return itemService.updateItem(id, itemDto, request, cookie);
    }

//    @GetMapping("/order/list")
//    public ResponseEntity<?> getOrderList(@RequestParam Long id) {
//        return itemService.getOrderList(id);
//    }
}
