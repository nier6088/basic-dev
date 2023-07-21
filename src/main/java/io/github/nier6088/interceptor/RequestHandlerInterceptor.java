package io.github.nier6088.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.nier6088.annotation.ClickLimit;
import io.github.nier6088.constants.ProjectConstants;
import io.github.nier6088.enums.ServiceStateCodeEnum;
import io.github.nier6088.util.GlobalRequestInfoThreadLocalUtil;
import io.github.nier6088.util.ResponseUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//用户来做请求的加工和过滤
@Order(-1)
@Component
public class RequestHandlerInterceptor implements HandlerInterceptor {

    RequestHandler signDataHandler = (req, res, m) -> {

        try {
            byte[] requestBody = StreamUtils.copyToByteArray(req.getInputStream());
            ObjectMapper objectMapper = new ObjectMapper();
            RequestSignData requestSignData = objectMapper.readValue(req.getInputStream(), RequestSignData.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    };
    RequestHandler requestClickLimitHandler = (req, res, handlerMethod) -> {
        Method method = handlerMethod.getMethod();
        ClickLimit annotation = method.getAnnotation(ClickLimit.class);
        if (Objects.isNull(annotation)) {
            return true;
        }

        long expire = TimeUnit.SECONDS.toMillis(annotation.expire());
        HttpSession session = req.getSession();
        Long lastRequestTime = (Long) session.getAttribute(ProjectConstants.BASE_REQUEST_LIMIT_SESSION_KEY);
        long currentTimeMillis = System.currentTimeMillis();

        // 如果上一次请求时间不存在 或者 请求时间超过限制的时间 则记录当前的请求时间
        if (Objects.isNull(lastRequestTime) || (currentTimeMillis - lastRequestTime >= expire)) {
            session.setAttribute(ProjectConstants.BASE_REQUEST_LIMIT_SESSION_KEY, currentTimeMillis);
            return true;
        }

        String message = annotation.message();

        ServiceStateCodeEnum serviceStateRequestLimitError = ServiceStateCodeEnum.SERVICE_STATE_REQUEST_LIMIT_ERROR;

        if (StringUtils.isNotBlank(message)) {
            //serviceStateRequestLimitError.setDescribe(message);
        }
        ResponseUtil.write(res, serviceStateRequestLimitError);
        return false;
    };

    RequestHandler requestIdHandler = (req, res, m) -> {
        String requestId = Optional.ofNullable(req.getHeader(ProjectConstants.REQUEST_HEADER_REQUEST_ID))
                .orElse(cn.hutool.core.lang.UUID.fastUUID().toString());
        MDC.put(ProjectConstants.MDC_REQUEST_ID, requestId);
        return true;
    };
    RequestHandler requestTenantHandler = (req, res, m) -> {
        GlobalRequestInfoThreadLocalUtil.setInfo(
                UUID.randomUUID().toString()
        );
        return true;
    };
    List<RequestHandler> stream = Stream.of(requestIdHandler, requestTenantHandler, requestClickLimitHandler).collect(Collectors.toList());

    public boolean handler(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        for (RequestHandler requestHandler : stream) {
            if (!requestHandler.handler(request, response, handlerMethod)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return this.handler(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
        GlobalRequestInfoThreadLocalUtil.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    @FunctionalInterface
    public interface RequestHandler {
        boolean handler(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod);
    }

    @Data
    class RequestSignData {
        private String data;
        private String sign;

    }
}
