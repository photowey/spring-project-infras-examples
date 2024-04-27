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
package io.github.photowey.spring.infras.web.example.aop;

import io.github.photowey.spring.infras.core.getter.BeanFactoryGetter;
import io.github.photowey.spring.infras.web.factory.matcher.AntPathMatcherFactory;
import io.github.photowey.spring.infras.web.factory.url.UrlPathHelperFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.util.UrlPathHelper;

/**
 * {@code AbstractAspect}
 *
 * @author photowey
 * @version 1.0.0
 * @since 2024/04/27
 */
public abstract class AbstractAspect implements BeanFactoryGetter, BeanFactoryAware {

    protected final UrlPathHelper HELPER = UrlPathHelperFactory.create();
    protected final AntPathMatcher requestMatcher = AntPathMatcherFactory.create(true);

    private ListableBeanFactory beanFactory;

    @Override
    public BeanFactory beanFactory() {
        return this.beanFactory;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }
}
