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
package io.github.photowey.spring.infras.bean.example.engine;

import io.github.photowey.spring.infras.bean.engine.AbstractEngineAwareBeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * {@code InfrasBeanEngineAwareBeanPostProcessor}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
public class InfrasBeanEngineAwareBeanPostProcessor extends AbstractEngineAwareBeanPostProcessor<InfrasBeanEngine> {

    @Override
    public boolean supports(Object bean) {
        return bean instanceof InfrasBeanEngineAware;
    }

    @Override
    public InfrasBeanEngine tryAcquireEngine(ConfigurableListableBeanFactory beanFactory) {
        return beanFactory.getBean(InfrasBeanEngine.class);
    }

    @Override
    public void inject(InfrasBeanEngine engine, Object bean) {
        ((InfrasBeanEngineAware) bean).setInfrasBeanEngine(engine);
    }
}