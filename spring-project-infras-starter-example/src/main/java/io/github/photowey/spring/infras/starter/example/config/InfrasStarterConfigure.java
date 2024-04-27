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
package io.github.photowey.spring.infras.starter.example.config;

import io.github.photowey.spring.infras.starter.example.engine.InfrasStarterEngineAwareBeanPostProcessor;
import io.github.photowey.spring.infras.starter.example.property.InfrasStarterProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * {@code InfrasStarterConfigure}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
@Configuration
@Import(InfrasStarterEngineAwareBeanPostProcessor.class)
@EnableConfigurationProperties(InfrasStarterProperties.class)
@EnableJpaRepositories(basePackages = "io.github.photowey.spring.infras.starter.example.repository")
public class InfrasStarterConfigure {}