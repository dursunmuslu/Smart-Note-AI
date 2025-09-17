package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "noteExecutor")
    public Executor noteExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);   // aynı anda kaç job çalışabilir
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(50); // queue kapasitesi
        executor.setThreadNamePrefix("NoteSummarize-");
        executor.initialize();
        return executor;
    }
}
