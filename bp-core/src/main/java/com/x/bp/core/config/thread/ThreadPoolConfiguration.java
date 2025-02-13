package com.x.bp.core.config.thread;

import org.springframework.context.annotation.Configuration;

/**
 * 线程池配置
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
@Configuration
public class ThreadPoolConfiguration {

//    @Bean(name = "searchHistoryPool")
//    public ThreadPoolTaskExecutor searchHistoryPool() {
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        //核心线程池大小
//        executor.setCorePoolSize(5);
//        //最大线程数
//        executor.setMaxPoolSize(5);
//        //队列容量
//        executor.setQueueCapacity(1000);
//        //活跃时间
//        executor.setKeepAliveSeconds(120);
//        //线程名字前缀
//        executor.setThreadNamePrefix("searchHistoryPool-");
//        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
//        executor.initialize();
//        return executor;
//    }
}
