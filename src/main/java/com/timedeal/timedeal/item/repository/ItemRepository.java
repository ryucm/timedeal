package com.timedeal.timedeal.item.repository;

import com.timedeal.timedeal.item.entity.Item;
import com.timedeal.timedeal.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByItemName(String itemName);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Item i where i.itemName = :itemName")
    Item findByIdWithPessimisticLock(@Param("itemName") String itemName);
}
