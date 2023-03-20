package com.timedeal.timedeal.item.service;

import com.timedeal.timedeal.item.dto.ItemDto;
import com.timedeal.timedeal.item.entity.Item;
import com.timedeal.timedeal.item.repository.ItemRepository;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final LoginUtil loginUtil;

    public ResponseEntity<?> createItem(ItemDto itemDto, Optional<Member> loginMember) {

        Item newItem = Item.builder()
                .itemName(itemDto.getItemName())
                .price(itemDto.getPrice())
                .stock(itemDto.getStock())
                .admin(loginMember.get())
                .startTime(itemDto.getStartTime())
                .build();

        Item savedItem = itemRepository.save(newItem);
        log.info(String.format("상품 등록 완료: Item Name = %s", savedItem.getItemName()));
        return ResponseEntity.success(savedItem, "상품 등록 완료");
    }

    public ResponseEntity<?> deleteItem(
            Long id,
            HttpServletRequest request,
            Cookie cookie) {

        Optional<Member> loginMember = loginUtil.login(request, cookie);
        Optional<Item> item = itemRepository.findById(id);

        if (!loginMember.get().getMemberId().equals(item.get().getAdmin().getMemberId())) {
            log.info(String.format("상품 삭제 권한이 없습니다."));
            return ResponseEntity.fail("상품 삭제 권한이 없습니다.");
        }
        itemRepository.deleteById(id);
        log.info(String.format("상품 삭제 완료"));
        return ResponseEntity.success("삭제하였습니다.");
    }

    public ResponseEntity<?> getItemList() {
        List<Item> itemList = itemRepository.findAll();
        List<ItemDto> itemDtoList = itemList.stream().map(ItemDto::new).collect(Collectors.toList());
        log.info(String.format("상품 목록 조회"));
        return ResponseEntity.success(itemDtoList,"성공");
    }

    public ResponseEntity<?> getItem(Long id) {
        ItemDto itemDto = itemRepository.findById(id).map(ItemDto::new).get();
        log.info(String.format("상품 조회. 상품 이름 : %s", itemDto.getItemName()));
        return ResponseEntity.success(itemDto, "성공");
    }

    public ResponseEntity<?> updateItem(Long id, ItemDto itemDto, HttpServletRequest request, Cookie cookie) {

        Optional<Member> loginMember = loginUtil.login(request, cookie);

        Optional<Item> item = itemRepository.findById(id);

        if (!loginMember.get().getMemberId().equals(item.get().getAdmin().getMemberId())) {
            log.info(String.format("상품 변경 권한이 없습니다."));
            return ResponseEntity.fail("상품 변경 권한이 없습니다.");
        }

        Item updateItem = Item.builder()
                .id(id)
                .itemName(itemDto.getItemName())
                .stock(itemDto.getStock())
                .price(itemDto.getPrice())
                .startTime(itemDto.getStartTime())
                .admin(loginMember.get())
                .build();
        Item savedItem = itemRepository.save(updateItem);
        log.info(String.format("상품 변경 완료. 상품 이름 : %s", savedItem.getItemName()));

        return ResponseEntity.success("성공");
    }



//    public ResponseEntity<?> getOrderList(Long id) {
//
//        Optional<Item> item = itemRepository.findById(id);
//        if (item == null) {
//            log.info(String.format("존재하지 않는 Item"));
//            return ResponseEntity.fail("해당 아이템이 존재하지 않습니다.");
//        }
//
//        List<Member> buyer = item.get().getBuyer();
//        List<MemberDto> memberDtoList = buyer.stream().map(MemberDto::new).collect(Collectors.toList());
//        return ResponseEntity.success(memberDtoList, "success");
//    }
}
