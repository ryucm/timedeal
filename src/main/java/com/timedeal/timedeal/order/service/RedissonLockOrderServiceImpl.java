package com.timedeal.timedeal.order.service;


import com.timedeal.timedeal.exception.Exceptions;
import com.timedeal.timedeal.item.entity.Item;
import com.timedeal.timedeal.item.repository.ItemRepository;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.order.entity.Orders;
import com.timedeal.timedeal.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedissonLockOrderServiceImpl implements OrderService{

    private final RedissonClient redissonClient;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    final int PURCHASE_NUM =  1;
    @Override
    public ResponseEntity<?> buyItem(Member member, String itemName) throws InterruptedException {

        RLock lock = redissonClient.getLock(itemName);

        try {
            boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);

            if (!available) {
                log.debug("Lock 획득 실패");
                return ResponseEntity.fail("구매 대기");
            }

            decrease(member, itemName);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return ResponseEntity.success("구매 성공");
    }

    private void decrease(Member member, String itemName) {
        // item 조회
        Item item = itemRepository.findByItemNameWithPessimisticLock(itemName);

        // 주문 내역 조회

        // 주문 가능 시간 확인
        item.checkSaleTime();

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
    }

    @Override
    public ResponseEntity<?> getOrderList(Member member, Pageable pageable) {
        return null;
    }

    @Override
    public ResponseEntity<?> getMemberList(Member member, Pageable pageable, Long itemId) {
        return null;
    }
}
