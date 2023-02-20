package com.nil1one.solog;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

@Aspect
@Slf4j
public class SoLogAspect {

    @Around("@annotation(soLog)")
    public Object around(ProceedingJoinPoint joinPoint, SoLog soLog) throws Throwable {
        Future<SoLogDO> o = proceedPre(soLog);
        try {
            Object o1 = joinPoint.proceed();
            proceedPost(o);
            return o1;
        } catch (Throwable throwable) {
            proceedPost(o);
            throw throwable;
        }
    }

    @Async
    public Future<SoLogDO> proceedPre(SoLog operationLog) {
        System.out.println("[OperationLogAspect] proceedPre");

        SoLogDO oLogDO = new SoLogDO();
        oLogDO.setDesc(operationLog.value());
        return new AsyncResult<>(oLogDO);
    }

    @Async
    public void proceedPost(Future<SoLogDO> o) {
        System.out.println("[OperationLogAspect] proceedPost");

        try {
            log.info("[OperationLogAspect] {{}}", o.get());
        } catch (Exception e) {
            log.error("[OperationLogAspect] {{}}", e.getMessage());
        }
    }
}
