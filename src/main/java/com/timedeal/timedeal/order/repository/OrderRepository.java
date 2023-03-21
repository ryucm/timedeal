package com.timedeal.timedeal.order.repository;

import com.timedeal.timedeal.order.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    boolean findByUserIdAndItemId(Long memberId, Long itemId);

    Page<Orders> findAllByMemberId(String memberId, Pageable pageable);

    Page<Orders> findAllByItemId(Long itemId, Pageable pageable);
}
