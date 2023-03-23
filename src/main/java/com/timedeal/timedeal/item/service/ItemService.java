package com.timedeal.timedeal.item.service;

import com.timedeal.timedeal.exception.ErrorCode;
import com.timedeal.timedeal.exception.Exceptions;
import com.timedeal.timedeal.item.dto.ItemDto;
import com.timedeal.timedeal.item.entity.Item;
import com.timedeal.timedeal.item.repository.ItemRepository;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final MemberUtil memberUtil;

    public ResponseEntity<?> createItem(ItemDto itemDto, Member loginMember) {

        memberUtil.checkItemPermission(loginMember);
        Item newItem = Item.builder()
                .itemName(itemDto.getItemName())
                .price(itemDto.getPrice())
                .stock(itemDto.getStock())
                .admin(loginMember)
                .startTime(itemDto.getStartTime())
                .build();

        Item savedItem = itemRepository.save(newItem);
        log.info(String.format("상품 등록 완료: Item Name = %s", savedItem.getItemName()));
        return ResponseEntity.success(savedItem, "상품 등록 완료");
    }

    public ResponseEntity<?> deleteItem(Long id, Member loginMember) {

        memberUtil.checkItemPermission(loginMember);
        itemRepository.deleteById(id);
        log.info("상품 삭제 완료");
        return ResponseEntity.success("삭제하였습니다.");
    }

    public ResponseEntity<?> getItemList(Pageable pageable) {

        Page<ItemDto> itemDtoList = itemRepository.findAll(pageable).map(ItemDto::new);
        log.info("상품 목록 조회");
        return ResponseEntity.success(itemDtoList,"성공");
    }

    public ResponseEntity<?> getItem(Long id) {
        ItemDto itemDto = itemRepository.findById(id).map(ItemDto::new).get();
        log.info(String.format("상품 조회. 상품 이름 : %s", itemDto.getItemName()));
        return ResponseEntity.success(itemDto, "성공");
    }

    public ResponseEntity<?> updateItem(Long id, ItemDto itemDto, Member loginMember) {

        Item item = itemRepository.findById(id)
                .orElseThrow(()-> new Exceptions(ErrorCode.NOT_FOUND_ITEM));
        memberUtil.checkItemPermission(loginMember);

        Item updateItem = item.updateItem(itemDto);
        Item savedItem = itemRepository.save(updateItem);
        log.info(String.format("상품 변경 완료. 상품 이름 : %s", savedItem.getItemName()));

        return ResponseEntity.success("성공");
    }

}
