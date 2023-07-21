package io.github.nier6088.constants;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class IgnoreConstants {


    //忽略log日志记录实体req序列化字符串的类型
    public final static List<Class> IGNORE_WEB_LOG_CLASS = Arrays.asList(
            MultipartFile.class,
            HttpServletRequest.class,
            HttpServletResponse.class
    );
    //忽略租户数据库表
    public final static List<String> IGNORE_TENANT_TABLE_NAME = Arrays.asList(
            "b_service_req_log_info",
            "b_service_error_log_info",
            "b_script_info",
            "b_script_tag_info"
    );

    public final static boolean filterWebLogClass(Object obj) {

        boolean flag = true;
        for (Class ignoreWebLogClass : IGNORE_WEB_LOG_CLASS) {
            boolean assignableFrom = obj.getClass().isAssignableFrom(ignoreWebLogClass);
            flag &= assignableFrom;
        }

        return flag;
    }


}
