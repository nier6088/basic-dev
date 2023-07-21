package io.github.nier6088.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @projectName: miquan-saas
 * @package: com.boolib.simple.constant
 * @className: BusConstant
 * @author: yangjun
 * @description: 业务常量类
 * @date: 2023/5/9 11:35
 * @version: 1.0
 */
public class BusConstant {

    /**
     * 系统不进行拦截的请求URL合集
     */
    public final static List<String> NO_FILTER_REQ_URLS = Arrays.asList(
            "wxPayBlindDateCardCallAyncCallback",
            "wxPayUnlockMateCallAsyncCallback",
            "receiveWxDataReq"
    );

    /**
     * 正式环境
     */
    public final static String PROFILES_ACTIVE_PROD = "prod";

    /**
     * 开发环境
     */
    public final static String PROFILES_ACTIVE_DEV = "dev";


    /**
     * MD5加密key -- 用于接口鉴权加密
     */
    public final static String SHA_KEY_REQ_VERIFY = "5678tujkghjkghj^&VB69lhkilhjklhjklhjklhdfg";

    /**
     * MD5加密key -- 用于获取替换入参是数组形式的value
     */
    public final static String SHA_KEY_REQ_ARRAY_PARAM_REPLACE_VALUE = "578ghjkghjkhRTYUgjlgjkrtyufhjfghj";

    /**
     * 玩家想玩剧本
     */
    public final static int USER_BEHAVIOR_SCRIPT_TYPE_WANT = 1;

    /**
     * 玩家玩过剧本
     */
    public final static int USER_BEHAVIOR_SCRIPT_TYPE_PLAYED = 2;

    /**
     * 待审核
     */
    public final static int TENANT_MERCHANT_APPLY_STATUS_AUDITING = 1;

    /**
     * 已通过
     */
    public final static int TENANT_MERCHANT_APPLY_STATUS_AUDITING_OK = 2;

    /**
     * 场次信息状态 待分享
     */
    public final static int SESSION_PAYMENT_STATUS_UNSHARED = 0;

    public final static int SESSION_PAYMENT_STATUS_PAY = 1;

    /**
     * 支付交易类型：微信小程序app
     */
    public static final int PAY_TRADE_TYPE_WXAPP = 1;

    /**
     * 支付状态: 支付中
     */

    public static final int PAY_TRADE_STATE_PAYING = 0;

    /**
     * 支付状态: 支付已完成
     */
    public static final int PAY_TRADE_STATE_FINISH = 1;

    /**
     * redis缓存基本信息时间12小时
     */
    public final static Long REDIS_CACHE_BASIC_TWELVE_HOUR = 3600L * 12;

    /**
     * redis缓存key -- 剧本的三个标签缓存值
     */
    public final static String REDIS_SCRIPT_SAAS_FHERE_LABEL_CACHE_KEY = "redisScriptSaasThereLabelCacheKey";

    /**
     * redis缓存key -- 平台剧本详情信息缓存
     */
    public final static String REDIS_PLATFORM_SCRIPT_DATA_INFO_KEY = "platformScriptDataInfoSaaSCacheKey";

    /**
     * 旧版本域名
     */
    public final static String CLIENT_SCRIPT_IMAGE_OLD_DOMAIN_URLS = "miquan-play.oss-cn-shenzhen.aliyuncs.com";

    /**
     * 新版本域名
     */
    public final static String CLIENT_SCRIPT_IMAGE_NEW_DOMAIN_URLS = "file.static.helloaba.cn";

    /**
     * 用户端-剧评剧本加权评分WR推荐语图片-神作必玩
     */
    public final static String CLIENT_USER_SCRIPT_WR_RECOMMEND_URL_ONE = "https://miquan-play.oss-cn-shenzhen.aliyuncs.com/data/clientImg/scriptRecommend/icon/7.png";
    /**
     * 用户端-剧评加权评分WR推荐语图片-出类拔萃
     */
    public final static String CLIENT_USER_SCRIPT_WR_RECOMMEND_URL_TWO = "https://miquan-play.oss-cn-shenzhen.aliyuncs.com/data/clientImg/scriptRecommend/icon/6.png";
    /**
     * 用户端-剧评加权评分WR推荐语图片-好评如潮
     */
    public final static String CLIENT_USER_SCRIPT_WR_RECOMMEND_URL_THERE = "https://miquan-play.oss-cn-shenzhen.aliyuncs.com/data/clientImg/scriptRecommend/icon/5.png";
    /**
     * 用户端-剧评加权评分WR推荐语图片-值得一玩
     */
    public final static String CLIENT_USER_SCRIPT_WR_RECOMMEND_URL_FOUR = "https://miquan-play.oss-cn-shenzhen.aliyuncs.com/data/clientImg/scriptRecommend/icon/4.png";
    /**
     * 用户端-剧评加权评分WR推荐语图片-可以试试
     */
    public final static String CLIENT_USER_SCRIPT_WR_RECOMMEND_URL_FIVE = "https://miquan-play.oss-cn-shenzhen.aliyuncs.com/data/clientImg/scriptRecommend/icon/3.png";

    /**
     * 用户端-剧评加权评分WR推荐语图片-随缘玩玩
     */
    public final static String CLIENT_USER_SCRIPT_WR_RECOMMEND_URL_SIX = "https://miquan-play.oss-cn-shenzhen.aliyuncs.com/data/clientImg/scriptRecommend/icon/2.png";

    /**
     * 用户端-剧评加权评分WR推荐语图片-评分不足
     */
    public final static String CLIENT_USER_SCRIPT_WR_RECOMMEND_URL_SEVEN = "https://miquan-play.oss-cn-shenzhen.aliyuncs.com/data/clientImg/scriptRecommend/icon/1.png";

    /**
     * 剧评标签展示的神作必玩的最低剧评条数
     */
    public final static Integer SCRIPT_EVALUATE_ONE_COUNT = 200;

    /**
     * 剧评标签展示的好评如潮的最低剧评条数
     */
    public final static Integer SCRIPT_EVALUATE_TWO_COUNT = 120;

    /**
     * 剧评标签展示最低剧评条数
     */
    public final static Integer SCRIPT_EVALUATE_THREE_COUNT = 75;

    public final static Integer SCRIPT_EVALUATE_FOUR_COUNT = 50;

    public final static Integer SCRIPT_EVALUATE_FIVE_COUNT = 30;

    /**
     * 神作必玩 数值
     */
    public final static Integer GOD_WILL_PLAY = 90;

    /**
     * 出类拔萃 数值
     */
    public final static Integer RISE_ABOVE_THE_COMMON_HERD = 85;

    /**
     * 好评如潮 数值
     */
    public final static Integer RAVE_REVIEWS = 80;
    /**
     * 值得一玩 数值
     */
    public final static Integer ITS_WORTH_PLAYING = 70;

    /**
     * 随缘玩玩 数值
     */
    public final static Integer PLAY_BY_CHANCE = 60;

    /**
     * redis缓存key -- 剧本推荐分获取
     */
    public final static String REDIS_SCRIPT_SAAS_RECOMMENDURL_AND_NUM_CACHE_KEY = "redisScriptSaaSRecommendurlAndNumCacheKey";

    public final static Long REDIS_CACHE_BASIC_DATA_TIME_ONE_HOUR = 3600L;
}
