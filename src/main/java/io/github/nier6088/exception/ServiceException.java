package io.github.nier6088.exception;


import io.github.nier6088.enums.ServiceStateCodeEnum;
import lombok.Data;

/**
 * @ClassName ServiceException
 * @description: 服务异常类
 * @author: yangjun
 * @create: 4/6/2022 4:38 PM
 * @Version V1.0.0
 **/
@Data
public class ServiceException extends RuntimeException {

    private int code;
    private String message;
    private Integer errorCode;

    public ServiceException(int errorCode, String message) {
        this.code = 400;
        this.message = message;
        this.errorCode = errorCode;
    }

    public ServiceException(ServiceStateCodeEnum serviceStateCodeEnum) {
        this.code = 400;
        this.message = serviceStateCodeEnum.getDescribe();
        this.errorCode = serviceStateCodeEnum.getCode();
    }

    public ServiceException(ServiceStateCodeEnum serviceStateCodeEnum, Exception ex) {
        super(ex);
        this.code = 400;
        this.message = serviceStateCodeEnum.getDescribe();
        this.errorCode = serviceStateCodeEnum.getCode();
    }


    public ServiceException(int code, ServiceStateCodeEnum serviceStateCodeEnum) {
        this.code = code;
        this.message = serviceStateCodeEnum.getDescribe();
        this.errorCode = serviceStateCodeEnum.getCode();
    }

    public ServiceException(int errorCode, String message, int code) {
        this.code = code;
        this.message = message;
        this.errorCode = errorCode;
    }

    public ServiceException(String message, int code) {
        this.code = code;
        this.message = message;
    }

    public ServiceException(Object o) {

    }
}