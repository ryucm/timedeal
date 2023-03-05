package com.timedeal.timedeal.item.service;

import com.timedeal.timedeal.item.dto.ItemDto;
import com.timedeal.timedeal.item.entity.Item;
import com.timedeal.timedeal.item.repository.ItemRepository;
import com.timedeal.timedeal.member.dto.response.MemberResponseDto;
import com.timedeal.timedeal.member.dto.response.ResponseEntity;
import com.timedeal.timedeal.member.entity.Member;
import com.timedeal.timedeal.member.entity.Role;
import com.timedeal.timedeal.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public ResponseEntity<?> createItem(
            ItemDto itemDto,
            HttpServletRequest request,
            Cookie cookie) {
        checkLogin(cookie);

        HttpSession session = request.getSession(false);
        Optional<Member> member = loginMember(session, cookie);

        Item newItem = Item.builder()
                .itemName(itemDto.getItemName())
                .price(itemDto.getPrice())
                .stock(itemDto.getStock())
                .member(member.get())
                .build();

        Item savedItem = itemRepository.save(newItem);
        log.info(String.format("상품 등록 완료: Item Name = %s", savedItem.getItemName()));
        return ResponseEntity.success(savedItem, "상품 등록 완료");
    }

    public ResponseEntity<?> deleteItem(
            Long id,
            HttpServletRequest request,
            Cookie cookie) {
        checkLogin(cookie);

        HttpSession session = request.getSession(false);
        Optional<Member> member = loginMember(session, cookie);
        Optional<Item> item = itemRepository.findById(id);

        if (!member.get().getMemberId().equals(item.get().getMember().getMemberId())) {
            return ResponseEntity.fail("상품 권한이 없습니다.");
        }
        itemRepository.deleteById(id);
        log.info(String.format("상품 삭제 완료"));
        return ResponseEntity.success("삭제하였습니다.");
    }

    public ResponseEntity<?> getItems() {
        return ResponseEntity.success("성공");
    }

    public ResponseEntity<?> getItem(Long id) {
        return ResponseEntity.success("성공");
    }

    public ResponseEntity<?> updateItem(ItemDto itemDto, HttpServletRequest request, Cookie cookie) {
        checkLogin(cookie);

        HttpSession session = request.getSession(false);
        Optional<Member> member = loginMember(session, cookie);
        return ResponseEntity.success("성공");
    }

    private void checkLogin(Cookie cookie) {
        if (cookie == null) {
            log.info(String.format("로그인이 필요합니다."));
            throw  new IllegalArgumentException();
        }
    }

    private Optional<Member> loginMember(HttpSession session, Cookie cookie) {
        MemberResponseDto findMember = (MemberResponseDto) session.getAttribute(cookie.getValue());
        Optional<Member> member = memberRepository.findByMemberId(findMember.getMemberId());

        if (member.get().getRole() == Role.USER) {
            log.info(String.format("상품 권한이 없습니다. 회원 권한 = %s", member.get().getRole()));
            throw  new IllegalArgumentException();
        }
        return member;
    }
}
