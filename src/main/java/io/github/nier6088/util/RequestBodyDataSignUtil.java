package io.github.nier6088.util;

import cn.hutool.extra.servlet.ServletUtil;
import io.github.nier6088.enums.ServiceStateCodeEnum;
import io.github.nier6088.exception.ClientException;
import io.github.nier6088.model.RequestBodyDataSignDTO;
import io.github.nier6088.wrapper.GlobalHttpServletRequestWrapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * @author wfq
 * @version 1.0
 * @description
 * @date 2023/6/5-10:57
 */

@Setter
@Component
@Slf4j
public class RequestBodyDataSignUtil {

    @Value("${request.sign.key}")
    private String key;

    @Autowired
    private HttpServletResponse httpServletResponse;

    private RequestBodyDataSignDTO getHeaderSignParam(HttpServletRequest httpServletRequest) {
        String body = ServletUtil.getBody(httpServletRequest);
        return ObjectMapperUtil.toObject(body, RequestBodyDataSignDTO.class);
    }


    /**
     * data sign 中data转换为实体对象 返回新的请求实体
     *
     * @param httpServletRequest
     * @return
     * @description
     * @author wfq
     * @date 2023/6/5-13:58
     * @version 1.0
     */
    public HttpServletRequest getNewHttpServletRequestFromDataSign(HttpServletRequest httpServletRequest) {
        RequestBodyDataSignDTO requestBodyDataSignParam = getHeaderSignParam(httpServletRequest);
        log.info("getNewHttpServletRequestFromDataSign：{}", httpServletRequest.getRequestURI());
        verify(requestBodyDataSignParam);
        String data = requestBodyDataSignParam.getData();
        byte[] body = Base64Utils.decodeFromString(data);

        try {
            return new GlobalHttpServletRequestWrapper(httpServletRequest, body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 前端传过来的data 消息摘要后对比是否一致
     *
     * @param requestBodyDataSignDTO
     * @description
     * @author wfq
     * @date 2023/6/5-17:36
     * @version 1.0
     */
    public void verify(RequestBodyDataSignDTO requestBodyDataSignDTO) {
        String data = requestBodyDataSignDTO.getData();
        String sign = requestBodyDataSignDTO.getSign();
        if (StringUtils.isAnyBlank(data, sign)) {
            throw new ClientException(ServiceStateCodeEnum.SERVICE_STATE_CLIENT_SIGN_ERROR);
        }
        String computeSign = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key.getBytes(StandardCharsets.UTF_8)).hmacHex(data.getBytes(StandardCharsets.UTF_8));
        boolean verify = StringUtils.equals(sign, computeSign);
        if (!verify) {
            ResponseUtil.write(httpServletResponse, ServiceStateCodeEnum.SERVICE_STATE_CLIENT_SIGN_ERROR);
            throw new RuntimeException("请求来源不正确,请求终止");
        }
    }

}
