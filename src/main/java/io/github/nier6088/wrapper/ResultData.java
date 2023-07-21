package io.github.nier6088.wrapper;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.nier6088.enums.ResultDataStatusEnum;
import io.github.nier6088.enums.ServiceStateCodeEnum;
import io.github.nier6088.util.ObjectMapperUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Version:
 * @Author: wfq
 * @date: 2023-04-27 17:53
 */
@Data
public class ResultData<T> {

    @JsonProperty(index = 1)
    private int code;

    @JsonProperty(index = 2)
    private String msg;

    @JsonProperty(index = 3)
    private T data;

    @JsonProperty(index = 4)
    private Integer errorCode;

    @JsonProperty(index = 5)
    private String errorMsg;


    public static <T> ResultData<T> ok(T data) {
        ResultData<T> tResultData = new ResultData<>();
        tResultData.setCode(ResultDataStatusEnum.SUCCESS.getCode());
        tResultData.setData(data);
        tResultData.setMsg("success");
        return tResultData;
    }

    public static ResultData okArray(Object data) {
        ResultData tResultData = new ResultData<>();
        tResultData.setCode(ResultDataStatusEnum.SUCCESS.getCode());
        Map map = new HashMap();
        map.put("items", data);
        tResultData.setData(map);
        tResultData.setMsg("success");
        return tResultData;
    }

    public static <T> ResultData<T> ok() {
        ResultData<T> tResultData = new ResultData<>();
        tResultData.setCode(ResultDataStatusEnum.SUCCESS.getCode());
        tResultData.setMsg("success");
        tResultData.setData(null);
        return tResultData;
    }


    public static <T> ResultData<T> fail(int errorCode, String errorMsg) {
        ResultData<T> tResultData = new ResultData<>();
        tResultData.setCode(ResultDataStatusEnum.FAIL.getCode());
//        tResultData.setCode(ServiceStateCodeEnum.SERVICE_CLIENT_ERROR.getCode());
//        tResultData.setMsg(ServiceStateCodeEnum.SERVICE_CLIENT_ERROR.getDescribe());
        tResultData.setErrorCode(errorCode);
        tResultData.setErrorMsg(errorMsg);
        return tResultData;
    }

    public static <T> ResultData<T> failClient(int errorCode, String errorMsg) {
        ResultData<T> tResultData = new ResultData<>();
        tResultData.setCode(ResultDataStatusEnum.FAIL.getCode());
//        tResultData.setCode(ServiceStateCodeEnum.SERVICE_CLIENT_ERROR.getCode());
//        tResultData.setMsg(ServiceStateCodeEnum.SERVICE_CLIENT_ERROR.getDescribe());
        tResultData.setErrorCode(errorCode);
        tResultData.setErrorMsg(errorMsg);
        return tResultData;
    }

    public static <T> ResultData<T> failClient(ServiceStateCodeEnum serviceStateCodeEnum) {
        ResultData<T> tResultData = new ResultData<>();
        tResultData.setCode(ResultDataStatusEnum.FAIL.getCode());
//        tResultData.setCode(ServiceStateCodeEnum.SERVICE_CLIENT_ERROR.getCode());
//        tResultData.setMsg(ServiceStateCodeEnum.SERVICE_CLIENT_ERROR.getDescribe());
        tResultData.setErrorCode(serviceStateCodeEnum.getCode());
        tResultData.setErrorMsg(serviceStateCodeEnum.getDescribe());
        return tResultData;
    }


    public static <T> ResultData<T> fail(ServiceStateCodeEnum serviceStateCodeEnum) {
        ResultData<T> tResultData = new ResultData<>();
        tResultData.setCode(400);
        tResultData.setCode(ServiceStateCodeEnum.SERVICE_STATE_ERROR.getCode());
        tResultData.setMsg(ServiceStateCodeEnum.SERVICE_STATE_ERROR.getDescribe());
        tResultData.setErrorCode(serviceStateCodeEnum.getCode());
        tResultData.setErrorMsg(serviceStateCodeEnum.getDescribe());
        return tResultData;
    }


    public static <T> ResultData<T> fail(ServiceStateCodeEnum serviceStateCodeEnum, String errorMsg) {
        ResultData<T> tResultData = new ResultData<>();
        tResultData.setCode(400);
        tResultData.setCode(ServiceStateCodeEnum.SERVICE_STATE_ERROR.getCode());
        tResultData.setMsg(ServiceStateCodeEnum.SERVICE_STATE_ERROR.getDescribe());
        tResultData.setErrorCode(serviceStateCodeEnum.getCode());
        tResultData.setErrorMsg(errorMsg);
        return tResultData;
    }

    public static <T> ResultData<T> makeClientReLoginRsp(int errorCode, String errorMsg) {
        ResultData<T> tResultData = new ResultData<>();

        tResultData.setCode(ResultCode.NOT_LOGIN.getCode());
        tResultData.setMsg(errorMsg);
        tResultData.setErrorCode(errorCode);
        tResultData.setErrorMsg(errorMsg);
        return tResultData;
    }

    @Override
    public String toString() {
        return ObjectMapperUtil.toStr(this);
    }
}
