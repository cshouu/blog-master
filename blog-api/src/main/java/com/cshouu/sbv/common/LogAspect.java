package com.cshouu.sbv.common;

import com.alibaba.fastjson.JSON;
import com.cshouu.sbv.utils.HttpContextUtils;
import com.cshouu.sbv.utils.IPUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect //切面 定义通知和切点的关系
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.cshouu.sbv.common.LogAnnotation)")
    public void pt(){}

    //环绕通知
    @Around("pt()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed(); //执行方法
        long time = System.currentTimeMillis() - beginTime;
        recordLog(joinPoint,time);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("==========log start==========");
        log.info("module:{}",logAnnotation.module());
        log.info("operator:{}",logAnnotation.operator());

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        log.info("request method:{}",className+"."+methodName+"()");

        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args[0]);
        log.info("params:{}",params);

        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.info("ip:{}", IPUtils.getIpAddr(request));

        log.info("excute time: {} ms",time);
        log.info("===========log end===========");
    }
}
