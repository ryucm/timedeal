package com.timedeal.timedeal.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_MATCHED_PASSWORD(HttpStatus.BAD_REQUEST,"MEMBER-NOT_MATCHED_PASSWORD","비밀번호가 일치하지 않습니다."),
    INVALID_PERMISSION(HttpStatus.FORBIDDEN,"PRODUCT-NOT_VALID_WRITER","상품 권한이 없습니다."),
    NOT_LOGIN(HttpStatus.BAD_REQUEST,"MEMBER-NOT_LOGIN","로그인이 필요합니다."),
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND,"MEMBER-NOT_FOUND_MEMBER","해당 유저를 찾을 수 없습니다."),
    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND,"MEMBER-NOT_FOUND_EMAIL","해당 이메일을 찾을 수 없습니다."),
    SOLD_OUT(HttpStatus.NOT_FOUND, "PRODUCT-ITEM_SOLD_OUT", "재고가 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
