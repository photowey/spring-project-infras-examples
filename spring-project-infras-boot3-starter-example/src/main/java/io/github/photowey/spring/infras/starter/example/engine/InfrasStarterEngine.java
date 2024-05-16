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

import io.github.photowey.spring.infras.bean.engine.Engine;
import io.github.photowey.spring.infras.bean.engine.notify.NotifyEngine;
import io.github.photowey.spring.infras.starter.example.engine.repository.RepositoryEngine;
import io.github.photowey.spring.infras.starter.example.engine.service.ServiceEngine;
import io.github.photowey.spring.infras.starter.example.property.InfrasStarterProperties;
import io.github.photowey.spring.infras.web.reader.RemoteResourceReader;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * {@code InfrasStarterEngine}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/17
 */
public interface InfrasStarterEngine extends Engine {

    RepositoryEngine repositoryEngine();

    ServiceEngine serviceEngine();

    NotifyEngine notifyEngine();

    // ----------------------------------------------------------------

    ThreadPoolTaskExecutor taskExecutor();

    RemoteResourceReader resourceReader();

    // ----------------------------------------------------------------

    InfrasStarterProperties infrasStarterProperties();
}
