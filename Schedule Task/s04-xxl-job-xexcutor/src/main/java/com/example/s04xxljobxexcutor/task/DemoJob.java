package com.example.s04xxljobxexcutor.task;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DemoJob {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicInteger counts = new AtomicInteger();

    @XxlJob("demoJobHandler")
    public void job1() {
        logger.info("[executeInternal]定时任务第 {} 次执行", counts.incrementAndGet());
    }

}