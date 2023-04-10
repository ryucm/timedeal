package com.timedeal.timedeal.order.service;


import com.timedeal.timedeal.item.entity.Item;
import com.timedeal.timedeal.item.repository.ItemRepository;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.order.entity.Orders;
import com.timedeal.timedeal.order.repository.OrderRepository;
import com.timedeal.timedeal.order.repository.RedisLockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LettuceLockOrderServiceImpl implements OrderService {

    private final RedisLockRepository redisLockRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    final int PURCHASE_NUM =  1;

    @Override
    public ResponseEntity<?> buyItem(Member member, String itemName) throws InterruptedException {
        while (!redisLockRepository.lock(itemName)) {
            Thread.sleep(100);
        }

        try {
            decrease(member, itemName);
        } finally {
            redisLockRepository.unlock(itemName);
        }
        return null;
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
