package com.x.bp.web.config;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSONObject;
import com.x.bp.common.context.ApiContext;
import com.x.bp.common.enums.LanguageTypeEnum;
import com.x.bp.common.exception.CommonBizException;
import com.x.bp.common.model.LoginUserTO;
import com.x.bp.common.enums.EnumError;
import com.x.bp.core.service.user.TokenService;
import com.x.bp.web.annotion.Callback;
import com.x.bp.web.annotion.LoginNotRequired;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;


/**
 * 登录拦截器处理
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Slf4j
@Component
public class UserLoginInterceptor implements HandlerInterceptor {
    @Resource
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String traceId = UUID.fastUUID().toString();
        MDC.put("tid", traceId);
        if (handler instanceof HandlerMethod) {
            log.info("Request URL: {}, Method:{}, Parameters:{} " , request.getRequestURL(), request.getMethod(), JSONObject.toJSONString(request.getParameterMap()));
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Callback callbackAnnotation = method.getAnnotation(Callback.class);
            if (callbackAnnotation != null) {
                return true;
            }
            String aifocusCookie = request.getHeader(MemberTokenFilter.AIFOCUS_COOKIE);
            String lang = request.getHeader(MemberTokenFilter.AIFOCUS_LANGUAGE);
            if (StringUtils.isBlank(lang)) {
                lang = LanguageTypeEnum.EN.getEnName();
            }
            LoginUserTO userInfo = tokenService.getUserInfo(aifocusCookie);
            ApiContext.getContext().setLoginUser(userInfo);
            ApiContext.getContext().setLange(lang);
            LoginNotRequired annotation = method.getAnnotation(LoginNotRequired.class);
            if (annotation != null) {
                return true;
            }
            if (Objects.isNull(userInfo) || userInfo.getId() == null) {
                throw new CommonBizException(EnumError.USER_NOT_LOGIN);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后执行，用于打印响应参数
        log.info("Response Status: {}," , response.getStatus());
        // 如果需要，可以打印响应内容等其他信息
        MDC.remove("tid");
    }
}
