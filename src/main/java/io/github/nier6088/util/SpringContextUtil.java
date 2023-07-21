package io.github.nier6088.util;


import io.github.nier6088.constants.BusConstant;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: ps-online
 * @ClassName SpringContextUtil
 * @description:
 * @author: yangjun
 * @create: 2022-04-12 16:39
 * @Version 1.0
 **/
@Component
public class SpringContextUtil implements ApplicationContextAware {

    /**
     * 上下文对象实例
     */
    private static ApplicationContext context;

    public static boolean isProdMode() {
        return !isDeveloperMode();
    }

    /**
     * 获取当前环境
     */
    public static String[] getActiveProfile() {
        return context.getEnvironment().getActiveProfiles();
    }

    public static boolean isDeveloperMode() {
        String[] activeProfile = getActiveProfile();
        for (String active : activeProfile) {
            if (BusConstant.PROFILES_ACTIVE_DEV.equalsIgnoreCase(active))
                return true;
        }
        return false;

    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 获取HttpServletRequest
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getDomain() {
        HttpServletRequest request = getHttpServletRequest();
        StringBuffer url = request.getRequestURL();
        return url.delete(url.length() - request.getRequestURI().length(), url.length()).toString();
    }

    public static String getOrigin() {
        HttpServletRequest request = getHttpServletRequest();
        return request.getHeader("Origin");
    }

    /**
     * 传入线程中
     */
    public static <T> T getBean(String beanName) {
        return (T) context.getBean(beanName);
    }

    public static String getEnv(String env) {
        return context.getEnvironment().getProperty(env);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static String getEnv(String env, String defaultValue) {
        return context.getEnvironment().getProperty(env, defaultValue);
    }
}
