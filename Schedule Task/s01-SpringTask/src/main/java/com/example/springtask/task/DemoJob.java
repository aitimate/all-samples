package com.example.springtask.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DemoJob {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicInteger counts = new AtomicInteger();

    @Scheduled(fixedRate = 3000, initialDelay = 1000)
    public void execute() {
        logger.info("[execute][定时第 {} 次执行]", counts.incrementAndGet());
    }
}