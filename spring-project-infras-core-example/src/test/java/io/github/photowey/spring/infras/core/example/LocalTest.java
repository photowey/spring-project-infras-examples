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
package io.github.photowey.spring.infras.core.example;

import io.github.photowey.spring.infras.core.example.property.InfrasCoreProperties;
import io.github.photowey.spring.infras.core.example.repository.EmployeeRepository;
import io.github.photowey.spring.infras.core.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * {@code LocalTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
public abstract class LocalTest {

    @Autowired
    protected EmployeeService employeeService;
    @Autowired(required = false)
    protected EmployeeRepository employeeRepository;

    @Autowired
    protected InfrasCoreProperties props;

    protected String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}