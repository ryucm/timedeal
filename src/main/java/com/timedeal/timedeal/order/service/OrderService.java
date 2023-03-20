package com.timedeal.timedeal.order.service;

import com.timedeal.timedeal.item.repository.ItemRepository;
import com.timedeal.timedeal.order.repository.OrderRepository;
import com.timedeal.timedeal.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final LoginUtil loginUtil;

//    public ResponseEntity<?> order(Long id) {
//        Optional<Item> item = itemRepository.findById(id);
//        if (item == null) {
//            log.info("해당 아이템이 존재하지 않습니다.");
//            return ResponseEntity.fail("Item이 존재하지 않습니다.");
//        }
//
//        List<MemberDto> buyer = item.get().getBuyer().stream().map(MemberDto::new).collect(Collectors.toList());
//        return ResponseEntity.success(buyer, "success");
//
//    }
}
