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
package io.github.photowey.spring.infras.bean.example.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.github.photowey.spring.infras.bean.example.App;
import io.github.photowey.spring.infras.bean.example.LocalTest;
import io.github.photowey.spring.infras.bean.example.core.domain.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@code JacksonJsonConverterTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/01
 */
@SpringBootTest(classes = App.class)
class JacksonJsonConverterTest extends LocalTest {

    @Test
    void testToJSONString() {
        Long now = 1714548766000L;
        Employee employee = Employee.builder()
                .id(now)
                .employeeNo("89757")
                .build();

        String json = this.jacksonJsonConverter.toJSONString(employee);
        Assertions.assertNotNull(json);

        DocumentContext ctx = JsonPath.parse(json);

        Assertions.assertEquals(employee.getId(), ctx.read("$.id"));
        Assertions.assertEquals(employee.getEmployeeNo(), ctx.read("$.employeeNo"));
    }

    @Test
    void testParseObject() {
        Long now = 1714548766000L;
        Employee employee = Employee.builder()
                .id(now)
                .employeeNo("89757")
                .build();

        String json = this.jacksonJsonConverter.toJSONString(employee);
        Assertions.assertNotNull(json);

        // ----------------------------------------------------------------

        Employee peer = this.jacksonJsonConverter.parseObject(json, Employee.class);
        Assertions.assertNotNull(peer);

        Assertions.assertEquals(employee.getId(), peer.getId());
        Assertions.assertEquals(employee.getEmployeeNo(), peer.getEmployeeNo());

        // ----------------------------------------------------------------

        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        Employee peer2 = this.jacksonJsonConverter.parseObject(bytes, Employee.class);
        Assertions.assertNotNull(peer2);

        Assertions.assertEquals(employee.getId(), peer2.getId());
        Assertions.assertEquals(employee.getEmployeeNo(), peer2.getEmployeeNo());

        // ----------------------------------------------------------------

        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        Employee peer3 = this.jacksonJsonConverter.parseObject(input, Employee.class);
        Assertions.assertNotNull(peer3);

        Assertions.assertEquals(employee.getId(), peer3.getId());
        Assertions.assertEquals(employee.getEmployeeNo(), peer3.getEmployeeNo());
    }

    // ----------------------------------------------------------------

    @Test
    void testToMap() {
        Long now = 1714548766000L;
        Employee employee = Employee.builder()
                .id(now)
                .employeeNo("89757")
                .build();

        Map<String, Object> ctx = this.jacksonJsonConverter.toMap(employee);
        Assertions.assertNotNull(ctx);

        Assertions.assertTrue(ctx.containsKey("id"));
        Assertions.assertTrue(ctx.containsKey("employeeNo"));
        Assertions.assertEquals(employee.getId(), ctx.get("id"));
        Assertions.assertEquals(employee.getEmployeeNo(), ctx.get("employeeNo"));
    }

    @Test
    void testToObject() {
        Long now = 1714548766000L;
        Employee employee = Employee.builder()
                .id(now)
                .employeeNo("89757")
                .build();

        Map<String, Object> ctx = this.jacksonJsonConverter.toMap(employee);
        Assertions.assertNotNull(ctx);

        Employee peer = this.jacksonJsonConverter.toObject(ctx, Employee.class);
        Assertions.assertNotNull(peer);

        Assertions.assertEquals(employee.getId(), peer.getId());
        Assertions.assertEquals(employee.getEmployeeNo(), peer.getEmployeeNo());
    }

    // ----------------------------------------------------------------

    @Test
    void testParseArray() {
        Long now = 1714548766000L;
        Employee employee = Employee.builder()
                .id(now)
                .employeeNo("89757")
                .build();

        List<Employee> employees = new ArrayList<>();
        employees.add(employee);

        String json = this.jacksonJsonConverter.toJSONString(employees);
        Assertions.assertNotNull(json);

        // ----------------------------------------------------------------

        List<Employee> peers1 = this.jacksonJsonConverter.parseArray(json, new TypeReference<List<Employee>>() {});
        Assertions.assertNotNull(json);
        Assertions.assertEquals(1, peers1.size());

        Employee peer = peers1.get(0);
        Assertions.assertEquals(employee.getId(), peer.getId());
        Assertions.assertEquals(employee.getEmployeeNo(), peer.getEmployeeNo());

        // ----------------------------------------------------------------

        byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
        List<Employee> peers2 = this.jacksonJsonConverter.parseArray(bytes, new TypeReference<List<Employee>>() {});
        Assertions.assertNotNull(json);
        Assertions.assertEquals(1, peers2.size());

        Employee peer2 = peers2.get(0);
        Assertions.assertEquals(employee.getId(), peer2.getId());
        Assertions.assertEquals(employee.getEmployeeNo(), peer2.getEmployeeNo());

        // ----------------------------------------------------------------

        ByteArrayInputStream input = new ByteArrayInputStream(bytes);
        List<Employee> peers3 = this.jacksonJsonConverter.parseArray(input, new TypeReference<List<Employee>>() {});
        Assertions.assertNotNull(json);
        Assertions.assertEquals(1, peers3.size());

        Employee peer3 = peers3.get(0);
        Assertions.assertEquals(employee.getId(), peer3.getId());
        Assertions.assertEquals(employee.getEmployeeNo(), peer3.getEmployeeNo());
    }
}