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
package io.github.photowey.spring.infras.starter.example.engine;

import io.github.photowey.spring.infras.bean.engine.AbstractEngine;
import io.github.photowey.spring.infras.bean.engine.notify.NotifyEngine;
import io.github.photowey.spring.infras.bean.notify.NotifyCenter;
import io.github.photowey.spring.infras.starter.example.engine.repository.RepositoryEngine;
import io.github.photowey.spring.infras.starter.example.engine.service.ServiceEngine;
import io.github.photowey.spring.infras.starter.example.property.InfrasStarterProperties;
import io.github.photowey.spring.infras.web.reader.RemoteResourceReader;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * {@code InfrasStarterEngineImpl}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/17
 */
@Component
public class InfrasStarterEngineImpl extends AbstractEngine implements InfrasStarterEngine {

    @Override
    public RepositoryEngine repositoryEngine() {
        return this.beanFactory().getBean(RepositoryEngine.class);
    }

    @Override
    public ServiceEngine serviceEngine() {
        return this.beanFactory().getBean(ServiceEngine.class);
    }

    @Override
    public NotifyEngine notifyEngine() {
        return this.beanFactory().getBean(NotifyEngine.class);
    }

    // ----------------------------------------------------------------

    @Override
    public ThreadPoolTaskExecutor taskExecutor() {
        return this.beanFactory().getBean(NotifyCenter.NOTIFY_EXECUTOR_BEAN_NAME, ThreadPoolTaskExecutor.class);
    }

    @Override
    public RemoteResourceReader resourceReader() {
        return this.beanFactory().getBean(RemoteResourceReader.class);
    }

    // ----------------------------------------------------------------

    @Override
    public InfrasStarterProperties infrasStarterProperties() {
        return this.beanFactory().getBean(InfrasStarterProperties.class);
    }
}