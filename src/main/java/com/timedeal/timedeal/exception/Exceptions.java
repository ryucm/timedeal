package com.timedeal.timedeal.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Exceptions extends RuntimeException {
    private ErrorCode errorCode;
}
