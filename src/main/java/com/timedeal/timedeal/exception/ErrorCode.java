package com.timedeal.timedeal.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_MATCHED_PASSWORD(HttpStatus.BAD_REQUEST,"MEMBER-NOT_MATCHED_PASSWORD","비밀번호가 일치하지 않습니다."),
    INVALID_PERMISSION(HttpStatus.FORBIDDEN,"ITEM-NOT_VALID_WRITER","상품 권한이 없습니다."),
    NOT_LOGIN(HttpStatus.BAD_REQUEST,"MEMBER-NOT_LOGIN","로그인이 필요합니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND,"MEMBER-NOT_FOUND_MEMBER","해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND,"MEMBER-NOT_FOUND_EMAIL","해당 이메일을 찾을 수 없습니다."),
    EXIST_MEMBER(HttpStatus.BAD_REQUEST,"MEMBER-EXITST_MEMBER","이미 존재하는 아이디입니다."),
    NOT_FOUND_ITEM(HttpStatus.NOT_FOUND,"ITEM-NOT_FOUND_ITEM", "해당 상품을 찾을 수 없습니다."),
    SOLD_OUT(HttpStatus.NOT_FOUND, "ITEM-ITEM_SOLD_OUT", "재고가 없습니다."),
    DUPLICATED_ORDER(HttpStatus.BAD_REQUEST, "ITEM-DUPLICATED_ORDER", "중복 구매는 불가합니다."),
    NOT_SALE_TIME(HttpStatus.BAD_REQUEST, "ITEM-NOT_SALE_TIME", "상품 판매 기간이 아닙니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
