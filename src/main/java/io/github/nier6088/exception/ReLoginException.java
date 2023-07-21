package io.github.nier6088.exception;


import io.github.nier6088.enums.ServiceStateCodeEnum;
import lombok.Getter;

/**
 * 抛出给客户端的异常
 *
 * @author wfq
 * @version 1.0
 * @description
 * @date 2023/5/26-14:11
 */

@Getter
public class ReLoginException extends ClientException {


    public ReLoginException(int errorCode, String message) {
        super(errorCode, message);
    }

    public ReLoginException(ServiceStateCodeEnum serviceStateCodeEnum) {
        super(serviceStateCodeEnum);

    }

    public ReLoginException(ServiceStateCodeEnum serviceStateCodeEnum, String errorMsg) {
        super(serviceStateCodeEnum, errorMsg);
    }
}
