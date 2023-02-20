package com.example.quartz.config;

import com.example.quartz.task.DemoJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleConfig {
    @Bean
    public JobDetail demoJob() {
        return JobBuilder.newJob(DemoJob.class)
                // demoJob
                .withIdentity("demoJob")
                // 是否在没有绑定Trigger时保留，默认值为false
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger demoJob01Trigger() {
        // 触发规则: 每 5 秒执行一次
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                // 频率
                .withIntervalInSeconds(3)
                // 次数
                .repeatForever();
        return TriggerBuilder.newTrigger()
                // 绑定demoJob
                .forJob(demoJob())
                // 任务标识名
                .withIdentity("demoJobTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}