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
package io.github.photowey.spring.infras.starter.example.reader;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import io.github.photowey.spring.infras.starter.example.App;
import io.github.photowey.spring.infras.starter.example.LocalTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;

/**
 * {@code NetworkResourceReaderTest}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/05/16
 */
@SpringBootTest(classes = App.class)
class NetworkResourceReaderTest extends LocalTest {

    @Test
    void testClasspathRead() {
        Resource resource = this.infrasStarterEngine.resourceReader().tryClasspathRead("dev/EffectiveRust.pdf");
        Assertions.assertNotNull(resource);
        Assertions.assertTrue(resource.isReadable());
    }

    @Test
    void tryNetworkRead() throws IOException {
        String uri = "https://httpbin.org/get?lang={lang}";

        Resource resource = this.infrasStarterEngine.resourceReader().tryNetworkRead(uri, (builder) -> {
            builder.queryParam("hello", "world");
        }, (components) -> {
            return components.expand("Java");
        });

        Assertions.assertNotNull(resource);
        Assertions.assertTrue(resource.isReadable());

        String json = this.infrasStarterEngine.resourceReader().read(resource);
        DocumentContext ctx = JsonPath.parse(json);

        Assertions.assertEquals("world", ctx.read("$.args.hello", String.class));
        Assertions.assertEquals("Java", ctx.read("$.args.lang", String.class));
    }
}