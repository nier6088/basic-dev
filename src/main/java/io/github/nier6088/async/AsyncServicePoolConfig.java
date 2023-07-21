package io.github.nier6088.async;


import io.github.nier6088.pool.DefaultMdcDecorator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author wfq
 * @version 1.0
 * @description
 * @date 2023/5/22-15:11
 */

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AsyncServicePoolConfig implements AsyncConfigurer {

    private final AsyncExceptionHandler asyncExceptionHandler;
    @Value("${thread.pool.executor.corePoolSize:10}")
    private int corePoolSize;
    @Value("${thread.pool.executor.maxPoolSize:20}")
    private int maxPoolSize;
    @Value("${thread.pool.executor.queueCapacity:100}")
    private int queueCapacity;
    @Value("${thread.pool.executor.keepAliveSeconds:60}")
    private int keepAliveSeconds;

    /**
     * 全局异步线程池
     *
     * @return
     * @description
     * @author wfq
     * @date 2023/5/22-16:46
     * @version 1.0
     */

    @Qualifier
    @Bean
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor defaultPoolTaskExecutor = new ThreadPoolTaskExecutor();
        defaultPoolTaskExecutor.setThreadNamePrefix("asyncPool");
        defaultPoolTaskExecutor.setCorePoolSize(corePoolSize);
        defaultPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        defaultPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        defaultPoolTaskExecutor.setQueueCapacity(queueCapacity);
        defaultPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        defaultPoolTaskExecutor.initialize();
        defaultPoolTaskExecutor.setTaskDecorator(new DefaultMdcDecorator());

        return defaultPoolTaskExecutor;
    }

    @Qualifier
    @Bean("activePool")
    public ThreadPoolTaskExecutor activePool() {
        ThreadPoolTaskExecutor defaultPoolTaskExecutor = new ThreadPoolTaskExecutor();
        defaultPoolTaskExecutor.setThreadNamePrefix("activePool");
        defaultPoolTaskExecutor.setCorePoolSize(10);
        defaultPoolTaskExecutor.setMaxPoolSize(20);
        defaultPoolTaskExecutor.setKeepAliveSeconds(60);
        defaultPoolTaskExecutor.setQueueCapacity(100);
        defaultPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        defaultPoolTaskExecutor.initialize();
        defaultPoolTaskExecutor.setTaskDecorator(new DefaultMdcDecorator());

        return defaultPoolTaskExecutor;
    }


    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return asyncExceptionHandler;
    }

}
