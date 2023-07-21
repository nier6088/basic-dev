package io.github.nier6088.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @version: java version 1.8
 * @Author: Administrator
 * @description:
 * @date: 2023-04-17 16:02
 */

@Data
@TableName("b_service_error_log_info")
public class ServiceErrorLogInfoEntity {
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;
    private String reqId;
    private String reqIp;
    private String userToken;
    private Long tenantId;
    private String errorReqUri;
    private String errorName;
    private String errorInfo;
    private String errorSource;
    private Date errorTime;
    private Date modifyTime;
}
