package com.timedeal.timedeal.facade;

import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.order.service.OptimisticLockOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OptimisticLockFacade {

    @Autowired
    private final OptimisticLockOrderServiceImpl optimisticLockOrderService;

    public void buyItem(Member member, String itemName) throws InterruptedException {
        while (true) {
            try {
                optimisticLockOrderService.buyItem(member, itemName);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
