package com.learning.rms.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.stereotype.Component;

@Component
public class AsyncConfig implements AsyncConfigurer {

	@Bean("asyncExecutor")
    public Executor getAsyncExecutor() {
        return Executors.newCachedThreadPool();
    }
}
