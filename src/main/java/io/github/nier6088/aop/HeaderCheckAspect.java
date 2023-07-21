package io.github.nier6088.aop;


import io.github.nier6088.annotation.HeaderCheck;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


/**
 * @Description 切面打印日志类
 * @Author yangjun
 * @Date 2021-01-26 20:22
 * @Version 1.0.0
 */
@Aspect
@Component
@Slf4j
public class HeaderCheckAspect {


    @Before("@annotation(headerCheck)")
    public void doBefore(JoinPoint joinPoint, HeaderCheck headerCheck) {

//        Object[] args = joinPoint.getArgs();
//        HeaderCheckEnum[] checks = headerCheck.check();
//        boolean checkUserToken = headerCheck.checkUserToken();
//
//        List<BaseReq> baseReqList = Arrays.stream(args)
//                .filter(item -> item instanceof BaseReq)
//                .map(item -> (BaseReq) item)
//                .collect(Collectors.toList());
//
//        if (CollectionUtils.isEmpty(baseReqList)) {
//            throw new ParamValidateException(400, "参数校验不正确");
//        }
//
//        BaseReq baseReq = baseReqList.get(0);
//        Long userToken = baseReq.getUserToken();
//        Long tenantId = baseReq.getTenantId();
//        String wxAppId = baseReq.getWxAppId();
//        Long shopId = baseReq.getShopId();
//
//        if (checkUserToken) {
//            AssertUtil.notNull(userToken, ServiceStateCodeEnum.SERVICE_STATE_USER_TOKEN_NULL_ERROR);
//        }
//
//        if (checks.length == 0) {
//            return;
//        }
//
//        Set<Integer> checkSet = new HashSet<>();
//        for (HeaderCheckEnum checkEnum : checks) {
//            String name = checkEnum.name();
//
//            if ("TAS".equals(name)) {
//                checkSet.add(HeaderCheckEnum.TENANT_ID.ordinal());
//                checkSet.add(HeaderCheckEnum.WX_APP_ID.ordinal());
//                checkSet.add(HeaderCheckEnum.SHOP_ID.ordinal());
//                break;
//            }
//            if ("TS".equals(name)) {
//                checkSet.add(HeaderCheckEnum.TENANT_ID.ordinal());
//                checkSet.add(HeaderCheckEnum.SHOP_ID.ordinal());
//                continue;
//            }
//            if ("TA".equals(name)) {
//                checkSet.add(HeaderCheckEnum.TENANT_ID.ordinal());
//                checkSet.add(HeaderCheckEnum.WX_APP_ID.ordinal());
//                continue;
//            }
//            if (HeaderCheckEnum.TENANT_ID.name().equals(name)) {
//                checkSet.add(HeaderCheckEnum.TENANT_ID.ordinal());
//                continue;
//            }
//            if (HeaderCheckEnum.WX_APP_ID.name().equals(name)) {
//                checkSet.add(HeaderCheckEnum.WX_APP_ID.ordinal());
//                continue;
//            }
//
//            if (HeaderCheckEnum.SHOP_ID.name().equals(name)) {
//                checkSet.add(HeaderCheckEnum.SHOP_ID.ordinal());
//                continue;
//            }
//
//            throw new ParamValidateException(ServiceStateCodeEnum.SERVICE_STATE_HEADER_VALIDATE_ERROR.getCode(), ServiceStateCodeEnum.SERVICE_STATE_HEADER_VALIDATE_ERROR.getDescribe());
//        }
//
//        checkSet.forEach(item -> {
//            if (item == HeaderCheckEnum.TENANT_ID.ordinal()) {
//                AssertUtil.notNull(tenantId, ServiceStateCodeEnum.SERVICE_STATE_HEADER_VALIDATE_ERROR);
//            }
//            if (item == HeaderCheckEnum.WX_APP_ID.ordinal()) {
//                AssertUtil.notNull(wxAppId, ServiceStateCodeEnum.SERVICE_STATE_PARAM_WX_APP_ID_VALIDATE_ERROR);
//            }
//            if (item == HeaderCheckEnum.SHOP_ID.ordinal()) {
//                AssertUtil.notNull(shopId, ServiceStateCodeEnum.SERVICE_STATE_PARAM_SHOP_ID_VALIDATE_ERROR);
//            }
//        });

    }


}
