package io.github.nier6088.aop;


import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.servlet.ServletUtil;
import io.github.nier6088.constants.BusConstant;
import io.github.nier6088.constants.ProjectConstants;
import io.github.nier6088.exception.ParamValidateException;
import io.github.nier6088.mapper.ServiceReqLogInfoMapper;
import io.github.nier6088.model.entity.ServiceReqLogInfoEntity;
import io.github.nier6088.util.ObjectMapperUtil;
import io.github.nier6088.util.ServletUtils;
import io.github.nier6088.util.SpringContextUtil;
import io.github.nier6088.wrapper.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.NamedThreadLocal;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description 切面打印日志类
 * @Author yangjun
 * @Date 2021-01-26 20:22
 * @Version 1.0.0
 */
@Aspect
@Order(-1)
@Component
@Slf4j
public class WebLogAspect implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    /**
     * 计算操作消耗时间
     */
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new NamedThreadLocal<Long>("Cost Time");

    @Autowired
    private ServiceReqLogInfoMapper serviceReqLogInfoMapper;


    /**
     * 切入点描述
     */
    //@Pointcut("@annotation(com.miquan.saas.common.annotation.SysLog)")
    @Pointcut("execution(public * com.boolib.*.api..*.*(..))")
    public void logPointCut() {
    }//签名，可以理解成这个切入点的一个名称

    /**
     * 切入点描述 这个是controller包的切入点
     */
    @Pointcut("execution(public * com.boolib.*.api..*.*(..))")
    public void paramValidate() {
    }

    @Before("paramValidate()")
    @Order(value = 1)
    public void doBefore1(JoinPoint joinPoint) {

        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    FieldError error = result.getFieldError();
                    throw new ParamValidateException(ResultCode.FAIL.getCode(), error.getField() + error.getDefaultMessage());
                }
            }
        }

    }

    @Before("logPointCut()")
    @Order(value = 2)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void doBefore2(JoinPoint joinPoint) {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        String requestUrl = request.getRequestURL().toString();

        String remoteIp = ServletUtil.getClientIP(request);

        boolean filterResult = BusConstant.NO_FILTER_REQ_URLS.stream().noneMatch(info -> requestUrl.contains(info));

        if (filterResult) {
            String requestURI = request.getRequestURL().toString();
            ServiceReqLogInfoEntity serviceReqLogInfo = new ServiceReqLogInfoEntity();
            Long id = IdUtil.getSnowflakeNextId();
            serviceReqLogInfo.setId(id);
            serviceReqLogInfo.setReqUrl(requestURI);
            serviceReqLogInfo.setReqId(MDC.get(ProjectConstants.MDC_REQUEST_ID));
            String method = ServletUtils.getRequest().getMethod();
            serviceReqLogInfo.setReqMethod(method);
            serviceReqLogInfo.setReqParams(ObjectMapperUtil.toStr(request.getParameterMap()));
            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String key = headerNames.nextElement();
                headers.put(key, request.getHeader(key));
            }
            String header = ObjectMapperUtil.toStr(headers);
            serviceReqLogInfo.setReqHeader(header);

            Object[] args = joinPoint.getArgs();

            String body = ObjectMapperUtil.toStr(args);
            serviceReqLogInfo.setReqBody(body);

            serviceReqLogInfo.setReqIp(remoteIp);

            serviceReqLogInfoMapper.insert(serviceReqLogInfo);

        }
    }

    @AfterReturning(value = "logPointCut()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        try {

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            HttpServletRequest request = attributes.getRequest();

            String requestUrl = request.getRequestURL().toString();
            String remoteIp = ServletUtil.getClientIP(request);

            // 处理完请求，返回内容
            boolean filterResult = BusConstant.NO_FILTER_REQ_URLS.stream().noneMatch(info -> requestUrl.contains(info));
            if (filterResult) {
                if (SpringContextUtil.isDeveloperMode()) {
                    System.out.println("[RequestURL] = " + requestUrl + " , [RequestIp] = " + remoteIp);
                    log.info("[RequestParam] = {}", ObjectMapperUtil.toStr(joinPoint.getArgs()));
                    log.info("[ResponseResult] = {}", ObjectMapperUtil.toStr(result));
                    log.info("[costTime] = {}ms ", System.currentTimeMillis() - TIME_THREADLOCAL.get());
                } else {
                    log.info("[RequestParam] = {}, {}, {}, [ResponseResult] = {}", remoteIp, requestUrl, ObjectMapperUtil.toStr(joinPoint.getArgs()), ObjectMapperUtil.toStr((result)));
                }
            }

        } finally {
            TIME_THREADLOCAL.remove();
        }
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        String requestUrl = request.getRequestURL().toString();
        String remoteIp = request.getRemoteAddr();
        if (SpringContextUtil.isDeveloperMode()) {
            System.out.println("[RequestURL] = " + requestUrl + " , [RequestIp] = " + remoteIp);
            log.info("[RequestParam] = {}", ObjectMapperUtil.toStr(joinPoint.getArgs()));
        } else {
            log.info("[RequestParam] = {}, {}, {}", remoteIp, requestUrl, ObjectMapperUtil.toStr(joinPoint.getArgs()));
        }

        TIME_THREADLOCAL.remove();
    }

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        Class<?> mainApplicationClass = event.getSpringApplication().getMainApplicationClass();
        System.out.println("mainApplicationClass.getPackage().getName() = " + mainApplicationClass.getPackage().getName());

    }
}
