package com.timedeal.timedeal.order.service;

import com.timedeal.timedeal.exception.ErrorCode;
import com.timedeal.timedeal.exception.Exceptions;
import com.timedeal.timedeal.item.entity.Item;
import com.timedeal.timedeal.item.repository.ItemRepository;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.order.entity.Orders;
import com.timedeal.timedeal.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    final int PURCHASE_NUM =  1;

    public synchronized ResponseEntity<?> buyItem(Member member, Long itemId) {
        if (orderRepository.findByUserIdAndItemId(member.getId(), itemId)) {
            throw new Exceptions(ErrorCode.DUPLICATED_ORDER);
        }

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new Exceptions(ErrorCode.NOT_FOUND_ITEM));
        if (item.isSoldOut(PURCHASE_NUM)) {
            throw new Exceptions(ErrorCode.SOLD_OUT);
        }

        if (!item.isSaleTime()) {
            throw new Exceptions(ErrorCode.NOT_SALE_TIME);
        }

        Orders newOrder = Orders.builder()
                .buyer(member)
                .product(item)
                .build();
        Orders completeOrder = orderRepository.save(newOrder);
        return ResponseEntity.success(completeOrder, "구매가 완료되었습니다.");
    }

    public ResponseEntity<?> getOrderList(Member member, Pageable pageable) {
        Page<Orders> ordersList = orderRepository.findAllByMemberId(member.getMemberId(), pageable);
        return ResponseEntity.success(ordersList, "유저가 구매한 상품 리스트 조회");
    }

    public ResponseEntity<?> getMemberList(Member member, Pageable pageable, Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new Exceptions(ErrorCode.NOT_FOUND_ITEM));
        if (!(item.getAdmin() == member)) {
            throw new Exceptions(ErrorCode.INVALID_PERMISSION);
        }
        Page<Orders> ordersList = orderRepository.findAllByItemId(itemId, pageable);
        return ResponseEntity.success(ordersList, "상품별 구매한 멤버 리스트 조회");
    }
}
