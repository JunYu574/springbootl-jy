package com.jy.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: JunYu
 * @Date: 2024/2/25 18:25
 * @Description:
 * @Version: V1.0.0
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取session，判断session用户信息是否存在
        Object user = request.getSession().getAttribute("userInfo");
        if(user == null){
            //未登录
            log.debug("未登录请求：" + request.getRequestURI());
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }
        log.debug("放行请求：" + request.getRequestURI());
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
