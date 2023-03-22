package com.timedeal.timedeal.order.repository;

import com.timedeal.timedeal.item.entity.Item;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.order.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    boolean findByBuyerAndProduct(Member member, Item item);

    Page<Orders> findAllByBuyer(Member member, Pageable pageable);

    Page<Orders> findAllByProduct(Item item, Pageable pageable);
}
