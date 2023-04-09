package com.timedeal.timedeal.service;

import com.timedeal.timedeal.exception.ErrorCode;
import com.timedeal.timedeal.exception.Exceptions;
import com.timedeal.timedeal.facade.OptimisticLockFacade;
import com.timedeal.timedeal.item.entity.Item;
import com.timedeal.timedeal.item.repository.ItemRepository;
import com.timedeal.timedeal.item.service.ItemService;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.entity.Role;
import com.timedeal.timedeal.member.repository.MemberRepository;
import com.timedeal.timedeal.order.repository.OrderRepository;
import com.timedeal.timedeal.order.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OptimisticLockFacade optimisticLockFacade;

    @BeforeEach
    void before() {
        orderRepository.deleteAll();
        itemRepository.deleteAll();
        memberRepository.deleteAll();

        Optional<Member> member = memberRepository.findByMemberId("memberId");
        if (!member.isPresent()) {
            Member newMember = Member.builder()
                    .email("memberEmail")
                    .password("password")
                    .memberId("memberId")
                    .role(Role.ADMIN)
                    .build();

            memberRepository.save(newMember);
        }

        Item item = Item.builder()
                .itemName("product")
                .price(1000)
                .stock(100)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.of(2024, 3, 30, 0, 0))
                .build();

        itemRepository.save(item);
    }

    @AfterEach
    void after() {


    }

    @Test
    @DisplayName("구매하기 Test")
    void order() throws InterruptedException {
        Member member = memberRepository.findByMemberId("memberId").orElseThrow(()-> new Exceptions(ErrorCode.NOT_FOUND_MEMBER));
//        orderService.buyItem(member, "product");
        optimisticLockFacade.buyItem(member, "product");
        assertEquals(99, itemRepository.findByItemName("product").get().getStock());
    }

    @Test
    @DisplayName("동시에 100개의 요청")
    void decrease() throws InterruptedException {

        // given
        Member member = memberRepository.findByMemberId("memberId").orElseThrow(()-> new Exceptions(ErrorCode.NOT_FOUND_MEMBER));
        int threadCount = 10;
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(()->{
                try {
                    optimisticLockFacade.buyItem(member, "product");
//                    orderService.buyItem(member, "product");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                latch.countDown();
            });
        }

        latch.await();

        // then
        Item item = itemRepository.findByItemName("product").orElseThrow(() -> new Exceptions(ErrorCode.NOT_FOUND_ITEM));
        assertEquals(90, item.getStock());

    }
}
