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
package io.github.photowey.spring.infras.core.example.config;

import io.github.photowey.spring.infras.core.context.ApplicationContextInjector;
import io.github.photowey.spring.infras.core.converter.jackson.DefaultJacksonJsonConverter;
import io.github.photowey.spring.infras.core.converter.jackson.JacksonJsonConverter;
import io.github.photowey.spring.infras.core.example.property.InfrasCoreProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * {@code InfrasCoreConfigure}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
@Configuration
@EnableJpaRepositories(basePackages = "io.github.photowey.spring.infras.core.example.repository")
@EnableConfigurationProperties(InfrasCoreProperties.class)
public class InfrasCoreConfigure {

    @Bean
    public ApplicationContextInjector applicationContextInjector() {
        return new ApplicationContextInjector();
    }

    @Bean(JacksonJsonConverter.JACKSON_JSON_CONVERTER_BEAN_NAME)
    public JacksonJsonConverter jacksonJsonConverter() {
        return new DefaultJacksonJsonConverter();
    }
}