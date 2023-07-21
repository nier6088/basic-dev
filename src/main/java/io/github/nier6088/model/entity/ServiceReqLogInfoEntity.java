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
@TableName("b_service_req_log_info")
public class ServiceReqLogInfoEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String reqId;
    private String reqUrl;
    private String reqHeader;
    private String reqMethod;
    private String reqBody;
    private String reqParams;
    private String reqIp;
    private String channelId;
    private String userToken;
    private Long tenantId;
    private Date createTime;
}
