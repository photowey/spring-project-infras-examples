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
package io.github.photowey.spring.infras.bean.example.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * {@code InfrasBeanProperties}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "io.github.photowey.infras.bean")
public class InfrasBeanProperties implements Serializable {

    private static final long serialVersionUID = 1341035278518813757L;

    private Cache cache = new Cache();

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Cache implements Serializable {

        private String loader;

        private long expired = TimeUnit.HOURS.toMillis(2);
        private TimeUnit unit = TimeUnit.MILLISECONDS;
    }

}