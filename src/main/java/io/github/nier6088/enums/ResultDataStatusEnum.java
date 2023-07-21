package io.github.nier6088.enums;

import lombok.Getter;

@Getter
public enum ResultDataStatusEnum {
    SUCCESS(1),
    FAIL(0),

    ;
    private final Integer code;

    ResultDataStatusEnum(Integer code) {
        this.code = code;
    }
}
