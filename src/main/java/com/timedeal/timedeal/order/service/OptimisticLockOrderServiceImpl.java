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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Version;

@Slf4j
@Service
@RequiredArgsConstructor
public class OptimisticLockOrderServiceImpl implements OrderService {
    @Autowired
    private final ItemRepository itemRepository;

    @Autowired
    private final OrderRepository orderRepository;

    final int PURCHASE_NUM =  1;

    @Override
    @Transactional
    public ResponseEntity<?> buyItem(Member member, String itemName){


        // item 조회
        Item item = itemRepository.findByItemNameWithOptimisticLock(itemName);


        // 주문 내역 조회
        log.info("주문 가능 시간 확인");
        // 주문 가능 시간 확인
        item.checkSaleTime();
        log.info("재고 변경");
        // 재고 변경
        log.info(String.format("기존 재고 : %s", item.getStock()));
        item.checkSoldOut(PURCHASE_NUM);
        itemRepository.save(item);
        log.info(String.format("변경 재고 : %s", item.getStock()));

        // 주문하기
        Orders newOrder = Orders.builder()
                .buyer(member)
                .product(item)
                .build();
        Orders completeOrder = orderRepository.save(newOrder);

        return ResponseEntity.success("구매가 완료되었습니다.");
    }

    @Override
    public ResponseEntity<?> getOrderList(Member member, Pageable pageable) {
        Page<Orders> ordersList = orderRepository.findAllByBuyer(member, pageable);
        return ResponseEntity.success(ordersList, "유저가 구매한 상품 리스트 조회");
    }

    @Override
    public ResponseEntity<?> getMemberList(Member member, Pageable pageable, Long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new Exceptions(ErrorCode.NOT_FOUND_ITEM));
        if (!(item.getAdmin() == member)) {
            throw new Exceptions(ErrorCode.INVALID_PERMISSION);
        }
        Page<Orders> ordersList = orderRepository.findAllByProduct(item, pageable);
        return ResponseEntity.success(ordersList, "상품별 구매한 멤버 리스트 조회");
    }
}
