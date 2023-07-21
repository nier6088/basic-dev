package io.github.nier6088.filter;


import io.github.nier6088.constants.BusConstant;
import io.github.nier6088.util.RequestBodyDataSignUtil;
import io.github.nier6088.util.SpringContextUtil;
import io.github.nier6088.wrapper.GlobalHttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器处理的是servlet。因为request读取一次之后，流就没有了，
 * 为了拦截器读取鉴权之后，控制器里面还能再次拿到请求数据，
 * 需要对流进行复制，然后再次通过过滤器写入到request里面。
 * 此过滤器的作用：复制request流，但对支付宝、微信回调以及请求入参中，复杂对象的，不起作用，故过滤排除，
 * 主要针对平台自己的APP请求。
 */
@Slf4j
@Order(-1)
@Component
public class GlobalHttpServletRequestReplacedFilter implements Filter {
    @Autowired
    private RequestBodyDataSignUtil requestBodyDataSignUtil;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {


        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            System.out.printf("\r\n请求URL:%s\r\n", ((HttpServletRequest) request).getRequestURL());
            String requestURL = ((HttpServletRequest) request).getRequestURL().toString();

            boolean filterResult = BusConstant.NO_FILTER_REQ_URLS.stream().noneMatch(info -> requestURL.contains(info));
            if (filterResult) {
                if (SpringContextUtil.isDeveloperMode()) {
                    requestWrapper = new GlobalHttpServletRequestWrapper((HttpServletRequest) request);
                } else {
                    requestWrapper = requestBodyDataSignUtil.getNewHttpServletRequestFromDataSign((HttpServletRequest) request);
                }
            }

        }

        if (requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) {

    }

}
