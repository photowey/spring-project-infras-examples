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
package io.github.photowey.spring.infras.web.example.aop.logging;

import io.github.photowey.spring.infras.web.aspect.AbstractAspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.core.io.InputStreamSource;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * {@code AbstractLoggingAspect}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
public abstract class AbstractLoggingAspect extends AbstractAspect implements EnvironmentAware {

    protected final List<String> LOGGING_SKIP_LIST = new ArrayList<>();

    protected Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Pointcut(
            "within(@org.springframework.stereotype.Repository *)" +
                    " || within(@org.springframework.stereotype.Service *)" +
                    " || within(@org.springframework.stereotype.Component *)"
    )
    public void componentPointcut() {}

    /**
     * // @AfterThrowing(pointcut = "controllerPointcut() && componentPointcut()", throwing = "e")
     *
     * @param joinPoint {@link JoinPoint}
     * @param e         {@link Throwable}
     */
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (environment.acceptsProfiles(Profiles.of("dev"))) {
            this.logger(joinPoint)
                    .error(
                            "exception in {}() with cause = '{}' and exception = '{}'",
                            joinPoint.getSignature().getName(),
                            e.getCause() != null ? e.getCause() : "NULL",
                            e.getMessage(),
                            e
                    );
        } else {
            this.logger(joinPoint)
                    .error(
                            "exception in {}() with cause = {}",
                            joinPoint.getSignature().getName(),
                            e.getCause() != null ? e.getCause() : "NULL"
                            , e);
        }
    }

    @Around("controllerPointcut()")
    public Object controllerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = this.logger(joinPoint);
        if (log.isDebugEnabled()) {
            log.debug("enter: {}() with argument[s] = {}", joinPoint.getSignature().getName(),
                    joinPoint.getArgs().length > 0
                            ? this.extractArgs(joinPoint.getArgs())
                            : Arrays.toString(joinPoint.getArgs())
            );
        }
        HttpServletRequest request = this.handleRequest();
        String uri = this.helper.getRequestUri(request);
        String method = request.getMethod();

        boolean matched = false;
        for (String pattern : LOGGING_SKIP_LIST) {
            if (this.requestMatcher.match(pattern, uri)) {
                matched = true;
                break;
            }
        }

        try {
            StopWatch watch = new StopWatch("t1");
            watch.start("t1");
            Object result = joinPoint.proceed();
            watch.stop();
            long rtt = watch.getTotalTimeMillis();
            if (log.isDebugEnabled()) {
                this.debugReport(joinPoint, log, matched, result);
            } else if (this.determinePrintResultEnabled()) {
                if (log.isInfoEnabled()) {
                    this.infoReport(joinPoint, log, matched, result);
                }
            }

            log.info("the web request: [{} {}], took time:[{}] ms", method.toUpperCase(), uri, rtt);
            return result;
        } catch (IllegalArgumentException e) {
            log.error("illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName());
            throw e;
        }
    }

    protected Object[] extractArgs(Object[] args) {
        List<Object> filterArgs = new ArrayList<>(args.length);

        for (Object arg : args) {
            if (!this.binaryType(arg)) {
                filterArgs.add(arg);
            }
        }

        return filterArgs.toArray();
    }

    protected boolean binaryType(Object target) {
        return target instanceof HttpServletRequest
                || target instanceof HttpServletResponse
                || target instanceof InputStreamSource
                || target instanceof InputStream
                || target instanceof OutputStream
                || target instanceof byte[];
    }

    protected HttpServletRequest handleRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    protected Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    private void infoReport(ProceedingJoinPoint joinPoint, Logger log, boolean matched, Object result) {
        Object rvt = this.binaryType(result) ? "binary" : result;
        log.info("exit: {}() with result = {}", joinPoint.getSignature().getName(), matched ? "skip.data" : rvt);
    }

    private void debugReport(ProceedingJoinPoint joinPoint, Logger log, boolean matched, Object result) {
        Object rvt = this.binaryType(result) ? "binary" : result;
        log.debug("exit: {}() with result = {}", joinPoint.getSignature().getName(), matched ? "skip.data" : rvt);
    }

    private boolean determinePrintResultEnabled() {
        // by configure
        return true;
    }
}
