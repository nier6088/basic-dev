package io.github.nier6088.util;


import org.slf4j.MDC;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @version: java version 1.8
 * @Author: Administrator
 * @description:
 * @date: 2023-04-17 9:21
 */
public class ThreadLocalPoolWrapUtil {

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }

            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }

            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

}
