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
package io.github.photowey.spring.infras.bean.example.service.impl;

import io.github.photowey.spring.infras.bean.example.core.domain.entity.Employee;
import io.github.photowey.spring.infras.bean.example.core.event.employee.EmployeeAddEvent;
import io.github.photowey.spring.infras.bean.example.core.event.employee.EmployeeQueryEvent;
import io.github.photowey.spring.infras.bean.example.engine.InfrasBeanEngine;
import io.github.photowey.spring.infras.bean.example.loader.EmployeeLoader;
import io.github.photowey.spring.infras.bean.example.property.InfrasBeanProperties;
import io.github.photowey.spring.infras.bean.example.repository.EmployeeRepository;
import io.github.photowey.spring.infras.bean.example.service.EmployeeService;
import io.github.photowey.spring.infras.core.context.ApplicationContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

/**
 * {@code EmployeeServiceImpl}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final InfrasBeanProperties props;

    private InfrasBeanEngine infrasBeanEngine;

    public EmployeeServiceImpl(InfrasBeanProperties props) {this.props = props;}


    @Override
    public void setInfrasBeanEngine(InfrasBeanEngine infrasBeanEngine) {
        this.infrasBeanEngine = infrasBeanEngine;
    }

    @Override
    public void add(Employee employee) {
        log.info("core: prepare add employee to database, id:[{}]", employee.getId());

        ConfigurableApplicationContext applicationContext = ApplicationContextHolder.INSTANCE.applicationContext();
        EmployeeRepository repository = applicationContext.getBean(EmployeeRepository.class);
        repository.save(employee);

        this.infrasBeanEngine.notifyEngine().notifyCenter().publishEvent(new EmployeeAddEvent(employee));
    }

    @Override
    public Employee load(Long employeeId) {
        Map<String, EmployeeLoader> beans = this.infrasBeanEngine.listableBeanFactory().getBeansOfType(EmployeeLoader.class);
        ArrayList<EmployeeLoader> employeeLoaders = new ArrayList<>(beans.values());
        AnnotationAwareOrderComparator.sort(employeeLoaders);

        for (EmployeeLoader employeeLoader : employeeLoaders) {
            if (employeeLoader.supports(this.props.getCache().getLoader())) {
                Employee employee = employeeLoader.load(employeeId);

                this.infrasBeanEngine.notifyEngine().notifyCenter().publishEvent(new EmployeeQueryEvent(employee));

                return employee;
            }
        }

        throw new UnsupportedOperationException("Unreachable here.");
    }
}