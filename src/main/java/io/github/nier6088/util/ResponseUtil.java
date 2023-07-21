package io.github.nier6088.util;


import io.github.nier6088.enums.ServiceStateCodeEnum;
import io.github.nier6088.wrapper.ResultData;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseUtil {
    public static void write(HttpServletResponse httpServletResponse, ServiceStateCodeEnum serviceStateCodeEnum) {
        try {
            String responseText = ResultData.failClient(serviceStateCodeEnum.getCode(), serviceStateCodeEnum.getDescribe()).toString();
            httpServletResponse.reset();
            httpServletResponse.setContentType("application/json;charset=utf-8");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.println(responseText);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
