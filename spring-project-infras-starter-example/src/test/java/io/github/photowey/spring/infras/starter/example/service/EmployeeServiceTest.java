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
package io.github.photowey.spring.infras.starter.example.service;

import io.github.photowey.spring.infras.core.context.ApplicationContextHolder;
import io.github.photowey.spring.infras.starter.example.App;
import io.github.photowey.spring.infras.starter.example.LocalTest;
import io.github.photowey.spring.infras.starter.example.core.domain.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

/**
 * {@code EmployeeServiceTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
@SpringBootTest(classes = App.class)
class EmployeeServiceTest extends LocalTest {

    @Test
    void testHolder() {
        ConfigurableApplicationContext applicationContext = ApplicationContextHolder.INSTANCE.applicationContext();
        Assertions.assertNotNull(applicationContext);
    }

    @Test
    void testRepository() {
        Assertions.assertNotNull(this.infrasStarterEngine.repositoryEngine().employeeRepository());
    }

    @Test
    void testAdd() {
        LocalDateTime now = LocalDateTime.now();
        Employee employee = Employee.builder()
                .id(System.currentTimeMillis())
                .organizationId(System.currentTimeMillis())
                .employeeNo(uuid() + "." + "random")
                .employeeName(uuid() + "." + "random")
                .createdBy(System.currentTimeMillis())
                .updatedBy(System.currentTimeMillis())
                .createdAt(now)
                .updateAt(now)
                .deleted(0)
                .build();

        this.infrasStarterEngine.serviceEngine().employeeService().add(employee);
    }

    @Test
    void testLoad() {
        Long employeeId = 17142075350009527L;

        String loader = this.infrasStarterEngine.infrasStarterProperties().getCache().getLoader();
        Assertions.assertEquals("database", loader);

        Employee employee = this.infrasStarterEngine.serviceEngine().employeeService().load(employeeId);

        Assertions.assertNotNull(employee);
        Assertions.assertTrue(employee.getEmployeeNo().contains("database"));
        Assertions.assertTrue(employee.getEmployeeName().contains("database"));
    }

    @Test
    void testUnsupportedLoader_with_default() {
        Long employeeId = 17142075350009527L;
        String loader = this.infrasStarterEngine.infrasStarterProperties().getCache().getLoader();
        Assertions.assertEquals("database", loader);

        this.infrasStarterEngine.infrasStarterProperties().getCache().setLoader("unknown");

        Employee employee = this.infrasStarterEngine.serviceEngine().employeeService().load(employeeId);

        Assertions.assertNotNull(employee);
        Assertions.assertTrue(employee.getEmployeeNo().contains("default"));
        Assertions.assertTrue(employee.getEmployeeName().contains("default"));

        this.infrasStarterEngine.infrasStarterProperties().getCache().setLoader("database");
    }
}