package io.github.nier6088.exception;

import lombok.Data;

/**
 * 服务异常类
 *
 * @author yangjun
 * @since 2018/12/6
 */
@Data
public class ParamValidateException extends RuntimeException {

    private int code;
    private String message;
    private Integer errorCode;

    public ParamValidateException(int errorCode, String message) {
        this.code = 400;
        this.message = message;
        this.errorCode = errorCode;
    }

    public ParamValidateException(int errorCode, String message, int code) {
        this.code = code;
        this.message = message;
        this.errorCode = errorCode;
    }

    public ParamValidateException(String message, int code) {
        this.code = code;
        this.message = message;
    }
}