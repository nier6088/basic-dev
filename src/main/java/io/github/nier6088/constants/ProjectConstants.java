package io.github.nier6088.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @version: java version 1.8
 * @Author: Administrator
 * @description:
 * @date: 2023-04-17 9:22
 */
public class ProjectConstants {
    public static final String ERROR_MSG = "服务器开小差了，请稍等片刻。";
    public static final String ERROR_MSG_FORMAT = "服务器开小差了，请稍等片刻 [%s]";
    public static final String MDC_TRACE_ID = "tid";
    public static final String MDC_REQUEST_ID = "reqId";
    public static final String MDC_REQUEST_TIME = "reqTime";
    //表中租户字段
    public static final String DB_TENANT_FIELD = "tenant_id";

    public static final int BEAN_ORDER_MAX = -1;

    public final static String MIQUAN_OSS_URL_PREFIX = "https://miquan-play.oss-cn-shenzhen.aliyuncs.com/";


    public final static List<String> NO_FILTER_REQ_URLS = Arrays.asList(
            "wxPayAyncCallback",
            "wxPayAyncCallbackV2",
            "aliPayAsyncCallBack",
            "aliPayAsyncCallBackV2",
            "delAllCacheInfo",
            "wxPayStageAyncCallback",
            "aliPayStageAsyncCallBack",
            "aliUserReleaseGroupAsyncCallBack",
            "aliUserReleaseGroupAsyncCallBackV2",
            "wxPayUserCreateGroupAyncCallback",
            "wxPayUserCreateGroupAyncCallbackV2",
            "aliPayUserPartnerCallBack",
            "wxPayUserPartnerCallBack",
            "wxPayCommodityGroupAyncCallback",
            "aliSpellGroupAsyncCallBack",
            "wxPaySpellGroupAyncCallback",
            "wxPayFreedomCardAyncCallback",
            "aliFreedomCardAsyncCallBack",
            "aliCommodityGroupAsyncCallBack");

    public final static String BASE_REQUEST_LIMIT_SESSION_KEY = "SUBMIT_LIMIT";
    public static final String REQUEST_HEADER_REQUEST_ID = "reqId";
    public static final String REQUEST_HEADER_TENANT_ID = "tenantId";
    public static final String REQUEST_HEADER_SHOP_ID = "shopId";
    public static final String REQUEST_HEADER_WX_APP_ID = "wxAppId";

    public static final String REQUEST_HEADER_PLATFORM_USER_TOKEN = "userToken";
    public static final String ORDER_SESSION_DIR = "orderSession";
}
