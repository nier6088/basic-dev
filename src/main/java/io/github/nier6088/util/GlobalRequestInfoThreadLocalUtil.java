package io.github.nier6088.util;


import io.github.nier6088.wrapper.GlobalRequestInfo;

public class GlobalRequestInfoThreadLocalUtil {

    private static final ThreadLocal<GlobalRequestInfo> threadLocal = new InheritableThreadLocal<>();


    public static void setInfo(GlobalRequestInfo globalRequestInfo) {
        threadLocal.set(globalRequestInfo);
    }

    public static void setInfo(String userToken) {
        GlobalRequestInfo globalRequestInfo = new GlobalRequestInfo();
        globalRequestInfo.setUserToken(userToken);
        threadLocal.set(globalRequestInfo);
    }


    public static void clear() {
        threadLocal.remove();
    }

    public static GlobalRequestInfo getRequestInfo() {
        return threadLocal.get();
    }

}
