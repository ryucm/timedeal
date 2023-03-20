package com.timedeal.timedeal.order.controller;

import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

//    @PostMapping
//    public ResponseEntity<?> order(Long id) {
//        return orderService.order(id);
//    }
}
