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
package io.github.photowey.spring.infras.starter.example.listener.employee;

import io.github.photowey.spring.infras.starter.example.core.domain.entity.Employee;
import io.github.photowey.spring.infras.starter.example.core.event.employee.EmployeeAddEvent;
import io.github.photowey.spring.infras.starter.example.listener.AbstractInfrasStarterEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * {@code EmployeeAddEventListener}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
@Slf4j
@Component
public class EmployeeAddEventListener extends AbstractInfrasStarterEventListener<EmployeeAddEvent> {

    @Override
    public void onApplicationEvent(EmployeeAddEvent event) {
        Employee employee = event.getEmployee();

        this.sync(employee);
    }

    public void sync(Employee employee) {
        log.info("infras.bean: receive employee add sync event, info:[{},{}]", employee.getId(), employee.getEmployeeNo());
    }
}