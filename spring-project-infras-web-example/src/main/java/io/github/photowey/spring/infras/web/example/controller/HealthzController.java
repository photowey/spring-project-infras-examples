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
package io.github.photowey.spring.infras.web.example.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * {@code HealthzController}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
@RestController
public class HealthzController {

    /**
     * POST :/healthz
     *
     * @return {@link Status}
     * @see <a href="http://localhost:7923/healthz">healthz</a>
     *
     * <pre>
     * io.github.photowey.spring.infras.web.example.controller.HealthzController exit: healthz() with result = <200 OK OK,HealthzController.Status(status=UP),[]>
     * io.github.photowey.spring.infras.web.example.controller.HealthzController the web request: [GET /healthz], took time:[12] ms
     * </pre>
     */
    @GetMapping("/healthz")
    public ResponseEntity<Status> healthz() {
        return ResponseEntity.ok(Status.up());
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Status implements Serializable {

        private static final long serialVersionUID = -4592962313231668949L;

        private String status;

        public static Status up() {
            return Status.builder().status("UP").build();
        }
    }
}