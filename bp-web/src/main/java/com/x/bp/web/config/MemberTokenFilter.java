package com.x.bp.web.config;


import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求过滤器
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Component
public class MemberTokenFilter implements Filter {
    public static final String AIFOCUS_COOKIE = "authorization";
    public static final String AIFOCUS_LANGUAGE = "language";


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest) ||
                !(response instanceof HttpServletResponse) ||
                request.getAttribute(getClass().getName()) != null) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        String origin = req.getHeader("Origin");
        if (origin != null && origin.matches("https://(www\\.)?business-promo\\.com")) {
            res.addHeader("Access-Control-Allow-Origin", origin);
        }
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Methods", "*");
//        res.addHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Expose-Headers," + AIFOCUS_COOKIE);
        res.addHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Language");
        res.addHeader("Access-Control-Expose-Headers", "*");
        res.addHeader("Access-Control-Max-Age", "1800");//30 min
        if ("OPTIONS".equals(((HttpServletRequest) request).getMethod())) {
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}
