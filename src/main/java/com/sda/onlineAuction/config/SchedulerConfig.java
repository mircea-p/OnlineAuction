package com.sda.onlineAuction.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Scheduled(fixedDelay = 5000)
    public void cronJob(){
        System.out.println("running cron job at: "+ LocalDateTime.now());
    }
}
