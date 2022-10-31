package com.example.s03quartzjdbc.task;// task/DemoJob.java

import com.example.s03quartzjdbc.service.DemoService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.atomic.AtomicInteger;

// 不允许并发执行: 重复任务(JobDetail::identity)会排队等待而不是并行执行
@DisallowConcurrentExecution
public class DemoJob extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicInteger counts = new AtomicInteger();

    @Autowired
    private DemoService demoService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        logger.info("[executeInternal][定时第 {} 次执行{}]", counts.incrementAndGet(), demoService);
    }
}