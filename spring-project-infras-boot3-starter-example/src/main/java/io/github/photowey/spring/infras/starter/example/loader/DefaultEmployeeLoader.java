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
package io.github.photowey.spring.infras.starter.example.loader;

import io.github.photowey.spring.infras.starter.example.core.domain.entity.Employee;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * {@code DefaultEmployeeLoader}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/17
 */
@Component
public class DefaultEmployeeLoader extends AbstractEmployeeLoader {

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    @Override
    public boolean supports(String strategy) {
        return true;
    }

    @Override
    public Employee load(Long employeeId) {
        LocalDateTime now = LocalDateTime.now();
        return Employee.builder()
                .id(employeeId)
                .organizationId(System.currentTimeMillis())
                .employeeNo(uuid() + ".default")
                .employeeName(uuid() + ".default")
                .createdBy(System.currentTimeMillis())
                .updatedBy(System.currentTimeMillis())
                .createdAt(now)
                .updateAt(now)
                .deleted(0)
                .build();
    }
}