package io.github.nier6088.wrapper;

/**
 * @ClassName ResultCode
 * @Description //响应码枚举类
 * @Author yangjun
 * @Date 3/31/2022 5:47 PM
 */
public enum ResultCode {

    // 登录失效、未登录
    NOT_LOGIN(110),

    // 成功
    SUCCESS(200),

    // 失败
    FAIL(400),

    // 未认证（签名错误）
    UNAUTHORIZED(401),

    // 非法调用接口
    ILLEGAL_REQ_SERVICE(403),

    // 接口不存在
    NOT_FOUND(404),

    // 用户不存在
    USER_NOT_EXIST(405),

    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }

    public Integer getCode() {

        return code;
    }

}
