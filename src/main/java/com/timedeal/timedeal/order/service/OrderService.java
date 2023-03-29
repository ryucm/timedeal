package com.timedeal.timedeal.order.service;

import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    public ResponseEntity<?> buyItem(Member member, String itemName);

    public ResponseEntity<?> getOrderList(Member member, Pageable pageable);

    public ResponseEntity<?> getMemberList(Member member, Pageable pageable, Long itemId);

}
