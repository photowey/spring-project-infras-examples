/*
 * Copyright © 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.photowey.spring.infras.web.example.config;

import io.github.photowey.spring.infras.bean.annotation.EnableInfrasComponents;
import io.github.photowey.spring.infras.bean.notify.NotifyCenter;
import io.github.photowey.spring.infras.web.example.engine.InfrasWebEngineAwareBeanPostProcessor;
import io.github.photowey.spring.infras.web.example.property.InfrasWebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * {@code InfrasWebConfigure}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
@Configuration
@EnableInfrasComponents
@Import(InfrasWebEngineAwareBeanPostProcessor.class)
@EnableConfigurationProperties(InfrasWebProperties.class)
@EnableJpaRepositories(basePackages = "io.github.photowey.spring.infras.web.example.repository")
public class InfrasWebConfigure {

    @Bean(NotifyCenter.NOTIFY_EXECUTOR_BEAN_NAME)
    public ThreadPoolTaskExecutor notifyAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(8);
        taskExecutor.setMaxPoolSize(8);
        taskExecutor.setQueueCapacity(1 << 10);

        taskExecutor.setKeepAliveSeconds(60);
        taskExecutor.setThreadGroupName("async");
        taskExecutor.setThreadNamePrefix("notify");

        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setAllowCoreThreadTimeOut(true);
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        return taskExecutor;
    }
}