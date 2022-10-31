package com.example.s03quartzjdbc.config;

import com.example.s03quartzjdbc.task.DemoJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduleConfig {

    // 或 手动注册，关键语句如下:
    // @Autowired
    // private Scheduler scheduler;
    // scheduler.scheduleJob(jobDetail, trigger);
    // scheduler.scheduleJob(jobDetail, Sets.newSet(trigger), true);

    @Bean
    public JobDetail demoJob() {
        // 每次调度都会重新创建实例，因此DemoJob应该继承QuartzJobBean
        return JobBuilder.newJob(DemoJob.class)
                // demoJob
                .withIdentity("demoJob")
                // 是否在没有绑定Trigger时保留，默认值为false
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger demoJobTrigger() {
        // 触发规则: 每 3 秒执行一次
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