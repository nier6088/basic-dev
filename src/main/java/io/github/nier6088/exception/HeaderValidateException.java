package io.github.nier6088.exception;


import io.github.nier6088.enums.ServiceStateCodeEnum;
import lombok.Data;

/**
 * 服务异常类
 *
 * @author yangjun
 * @since 2018/12/6
 */
@Data
public class HeaderValidateException extends RuntimeException {

    private String message;
    private Integer errorCode;

    public HeaderValidateException() {
    }

    public HeaderValidateException(ServiceStateCodeEnum serviceStateCodeEnum) {
        this.errorCode = serviceStateCodeEnum.getCode();
        this.message = serviceStateCodeEnum.getDescribe();
    }

}