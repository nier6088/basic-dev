package io.github.nier6088.exception;

import io.github.nier6088.enums.ServiceStateCodeEnum;
import lombok.Getter;

/**
 * @author wfq
 * @version 1.0
 * @description
 * @date 2023/5/26-14:11
 */
@Getter
public class ClientException extends RuntimeException {
    private final int errorCode;
    private final String errorMsg;

    public ClientException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMsg = message;
    }

    public ClientException(ServiceStateCodeEnum serviceStateCodeEnum) {
        super(serviceStateCodeEnum.getDescribe());
        this.errorCode = serviceStateCodeEnum.getCode();
        this.errorMsg = serviceStateCodeEnum.getDescribe();
    }

    public ClientException(ServiceStateCodeEnum serviceStateCodeEnum, String errorMsg) {
        super(errorMsg);
        this.errorCode = serviceStateCodeEnum.getCode();
        this.errorMsg = errorMsg;
    }
}
