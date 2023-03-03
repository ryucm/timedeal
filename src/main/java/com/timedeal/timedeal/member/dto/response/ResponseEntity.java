package com.timedeal.timedeal.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseEntity<T> {
    private boolean success;
    private T data = null;
    private T message;

    public static <T> ResponseEntity<T> success(T msg) {
        return new ResponseEntity<>(true, null, msg);
    }
    public static <T> ResponseEntity<T> success(T data, T msg) {
        return new ResponseEntity<>(true, data, msg);
    }

    public static <T> ResponseEntity<T> fail(T msg) {
        return new ResponseEntity<>(false, null, msg);
    }

    public static <T> ResponseEntity<T> fail(T data,T msg) {
        return new ResponseEntity<>(false, data, msg);
    }
}
