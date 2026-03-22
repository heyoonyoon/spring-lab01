package com.pattern.springlab.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다."),

    // 유저
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),

    // 메모
    MEMO_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 메모입니다.");

    private final HttpStatus status;
    private final String message;
}