package io.github.nier6088.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.hutool.core.exceptions.ExceptionUtil;
import io.github.nier6088.SimpleProjectApplicationAutoConfiguration;
import io.github.nier6088.constants.ProjectConstants;
import io.github.nier6088.enums.ServiceStateCodeEnum;
import io.github.nier6088.mapper.ServiceReqErrorLogInfoMapper;
import io.github.nier6088.model.entity.ServiceErrorLogInfoEntity;
import io.github.nier6088.util.ServletUtils;
import io.github.nier6088.wrapper.ResultData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static io.github.nier6088.enums.ServiceStateCodeEnum.*;

/**
 * @ClassName ExceptionsHandler
 * @description: 全局异常处理
 * @author: yangjun
 * @create: 4/6/2022 4:21 PM
 * @Version V1.0.0
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionsHandler {

    @Autowired
    private ServiceReqErrorLogInfoMapper serviceReqErrorLogInfoMapper;

    /**
     * @return ResponseResult
     * @description: 请求路径错误异常
     * @author yangjun
     * @date 4/6/2022 4:40 PM
     * @Version V1.0.0
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultData handleNoHandlerFoundException() {

        return ResultData.fail(SERVICE_STATE_SERVER_NOT_FOUND);
    }

    @ExceptionHandler(HeaderValidateException.class)
    public ResultData handleHeaderValidateException(HeaderValidateException headerValidateException) {
        return ResultData.fail(ServiceStateCodeEnum.SERVICE_STATE_HEADER_VALIDATE_ERROR);
    }

    @ExceptionHandler(ParamValidateException.class)
    public ResultData handleParamValidateException(ParamValidateException paramValidateException) {
        Integer errorCode = paramValidateException.getErrorCode();
        String message = paramValidateException.getMessage();
        return ResultData.fail(errorCode, message);
    }

    @ExceptionHandler(NotLoginException.class)
    public ResultData handleNotLoginException(NotLoginException notLoginException) {
        return ResultData.failClient(SERVICE_STATE_ACCOUNT_NOT_LOGIN_ERROR);
    }


    @ExceptionHandler(NotPermissionException.class)
    public ResultData handleNotPermissionException(NotPermissionException notPermissionException) {
        return ResultData.failClient(SERVICE_STATE_ACCOUNT_NO_PERMISSION_ERROR);
    }

    /**
     * @param e
     * @return ResponseResult
     * @description: 请求参数验证异常
     * @author yangjun
     * @date 4/6/2022 4:58 PM
     * @Version V1.0.0
     */
    @ExceptionHandler(BindException.class)
    public ResultData handBindException(BindException e) {

        log.error(e.getMessage(), e);

        BindingResult bindingResult = e.getBindingResult();

        String errMsg = "请求参数异常";

        if (bindingResult.hasErrors()) {

            FieldError fieldError = bindingResult.getFieldError();

            if (fieldError != null) {

                // errMsg = fieldError.getField() + fieldError.getDefaultMessage();
                errMsg = fieldError.getDefaultMessage();
            }
        }

        return ResultData.failClient(ServiceStateCodeEnum.SERVICE_STATE_PARAM_VALIDATE_ERROR.getCode(), errMsg);
    }

    @ExceptionHandler(ClientException.class)
    public ResultData handleClientException(ClientException clientException) {
        Integer errorCode = clientException.getErrorCode();
        String message = clientException.getMessage();
        return ResultData.failClient(errorCode, message);
    }


    /**
     * @return ResponseResult
     * @description: 服务错误异常
     * @author yangjun
     * @date 4/6/2022 4:40 PM
     * @Version V1.0.0
     */
    @ExceptionHandler(ServiceException.class)
    public ResultData handleServiceException(ServiceException e) {
        log.error(e.getMessage(), e);
        insertError(e);
        return ResultData.fail(e.getErrorCode(), e.getMessage());
    }


    /**
     * @param e
     * @return ResponseResult
     * @description: 请求参数验证异常
     * @author yangjun
     * @date 4/6/2022 4:58 PM
     * @Version V1.0.0
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultData handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        log.error(e.getMessage(), e);

        BindingResult bindingResult = e.getBindingResult();

        String errMsg = "请求参数异常";

        if (bindingResult.hasErrors()) {

            FieldError fieldError = bindingResult.getFieldError();

            if (fieldError != null) {

                // errMsg = fieldError.getField() + fieldError.getDefaultMessage();
                errMsg = fieldError.getDefaultMessage();
            }
        }

        return ResultData.fail(ServiceStateCodeEnum.SERVICE_STATE_PARAM_VALIDATE_ERROR, errMsg);
    }

    /**
     * @param e
     * @return ResponseResult
     * @description: 数据库触发唯一索引异常
     * @author yangjun
     * @date 4/6/2022 5:00 PM
     * @Version V1.0.0
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResultData handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return ResultData.failClient(SERVICE_STATE_DATA_DUPLICATE_KEY.getCode(), SERVICE_STATE_DATA_DUPLICATE_KEY.getDescribe());
    }


    /**
     * @param request
     * @param e
     * @return ResponseResult
     * @description: 总异常处理
     * @author yangjun
     * @date 4/6/2022 4:36 PM
     * @Version V1.0.0
     */
    @ExceptionHandler(Exception.class)
    public ResultData handleException(HttpServletRequest request, Exception e) {
        String reqURI = request.getRequestURI();
        log.error(reqURI, e.getMessage(), e);
        insertError(e);
        return ResultData.fail(SERVICE_STATE_ERROR);
    }

    /**
     * @return
     * @author wfq
     * @description 异常信息入库
     * @date 2023/5/12 11:23
     * @version 1.0
     **/
    public void insertError(Exception e) {
        try {
            Date date = new Date();
            HttpServletRequest request = ServletUtils.getRequest();
            String requestURI = request.getRequestURL().toString();
            String requestId = MDC.get(ProjectConstants.MDC_REQUEST_ID);

            ServiceErrorLogInfoEntity serviceErrorLogInfo = new ServiceErrorLogInfoEntity();
            serviceErrorLogInfo.setErrorTime(date);
            serviceErrorLogInfo.setReqId(requestId);
            serviceErrorLogInfo.setErrorName(e.getMessage());
            serviceErrorLogInfo.setErrorReqUri(requestURI);

            String errorStack = getStackTrace(e);
            serviceErrorLogInfo.setErrorInfo(errorStack);

            String errorSource = getErrorSource(e);
            serviceErrorLogInfo.setErrorSource(errorSource);

            String remoteAddr = request.getRemoteAddr();
            serviceErrorLogInfo.setReqIp(remoteAddr);

            serviceReqErrorLogInfoMapper.insert(serviceErrorLogInfo);
        } catch (Exception exception) {
            log.error("插入错误日志表失败,源异常：{}", e, exception);
        }
    }

    private String getErrorSource(Exception e) {
        Package aPackage = SimpleProjectApplicationAutoConfiguration.class.getPackage();
        String name = aPackage.getName();

        StackTraceElement[] stackTrace = e.getStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < stackTrace.length; i++) {
            String className = stackTrace[i].getClassName();
            String methodName = stackTrace[i].getMethodName();
            int lineNumber = stackTrace[i].getLineNumber();
            if (className.startsWith(name) && lineNumber != -1) {
                String fileName = stackTrace[i].getFileName();
                stringBuilder.append(className)
                        .append("#")
                        .append(methodName)
                        .append(", ")
                        .append(fileName)
                        .append(":")
                        .append(lineNumber)
                        .append("; \r\n ");
            }


        }
        String errorSource = stringBuilder.toString();

        log.info("errorSource =[ \r\n {} ] ", errorSource);

        if (StringUtils.isNotBlank(errorSource)) {
            return errorSource;
        }

        String className = stackTrace[0].getClassName();
        String methodName = stackTrace[0].getMethodName();
        String fileName = stackTrace[0].getFileName();
        int lineNumber = stackTrace[0].getLineNumber();

        return className + "#" + methodName + ", " + fileName + ":" + lineNumber;

    }

    private String getStackTrace(Exception e) {
        return ExceptionUtil.stacktraceToOneLineString(e, 500);
    }


}