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
package io.github.photowey.spring.infras.starter.example.engine.repository;

import io.github.photowey.spring.infras.bean.engine.AbstractEngine;
import io.github.photowey.spring.infras.starter.example.repository.EmployeeRepository;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * {@code RepositoryEngineImpl}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
@Getter
@Component
@Accessors(fluent = true)
public class RepositoryEngineImpl extends AbstractEngine implements RepositoryEngine {

    @Autowired
    private EmployeeRepository employeeRepository;
}