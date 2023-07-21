package io.github.nier6088.async;


import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author wfq
 * @version 1.0
 * @description
 * @date 2023/5/22-17:22
 */

@Slf4j
@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        ex.printStackTrace();
        log.error("async 出现异常 method:{} , params:{} ,", method, params, ex);

    }

}
