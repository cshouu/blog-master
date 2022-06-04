package com.cshouu.sbv.handler;

import com.alibaba.fastjson.JSON;
import com.cshouu.sbv.dao.pojo.SysUser;
import com.cshouu.sbv.service.LoginService;
import com.cshouu.sbv.utils.UserThreadLocal;
import com.cshouu.sbv.vo.ErrorCode;
import com.cshouu.sbv.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行controller方法（handler）之前执行
        /**
         * 1.判断请求路径是不是handler方法HandlerMethod
         * 2.判断token是否为空，为空-未登录
         * 3.token不为空，校验token验证登录，成功放行
         */
        if(!(handler instanceof HandlerMethod)){
            //handler可能是RequesResourceHandler
            //springboot访问静态资源默认到classpath下的static去查找
            return true;
        }
        String token = request.getHeader("Authorization");
        log.info("============request start==============");
        log.info("request uri:{}",request.getRequestURI());
        log.info("request method:{}",request.getMethod());
        log.info("token:{}",token);
        log.info("============request end==============");
        if(StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        SysUser sysUser = loginService.checkToken(token);
        if(sysUser==null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(),ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        //在controller中直接获取用户信息
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //需要移除用完的数据，否则有内存泄漏的风险（垃圾回收、弱引用）
        UserThreadLocal.remove();
    }
}
