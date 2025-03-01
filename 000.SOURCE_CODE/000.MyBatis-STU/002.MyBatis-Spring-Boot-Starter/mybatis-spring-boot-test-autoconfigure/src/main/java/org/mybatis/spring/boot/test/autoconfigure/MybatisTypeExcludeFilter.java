/*
 *    Copyright 2015-2025 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.spring.boot.test.autoconfigure;

import java.util.Collections;
import java.util.Set;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.filter.AnnotationCustomizableTypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotatedElementUtils;

/**
 * {@link TypeExcludeFilter} for {@link MybatisTest @MybatisTest}.
 *
 * @author wonwoo
 *
 * @since 1.2.1
 */
class MybatisTypeExcludeFilter extends AnnotationCustomizableTypeExcludeFilter {
  private final MybatisTest annotation;

  MybatisTypeExcludeFilter(Class<?> testClass) {
    this.annotation = AnnotatedElementUtils.getMergedAnnotation(testClass, MybatisTest.class);
  }

  @Override
  protected boolean hasAnnotation() {
    return this.annotation != null;
  }

  @Override
  protected ComponentScan.Filter[] getFilters(FilterType type) {
    switch (type) {
      case INCLUDE:
        return this.annotation.includeFilters();
      case EXCLUDE:
        return this.annotation.excludeFilters();
      default:
        throw new IllegalStateException("Unsupported type " + type);
    }
  }

  @Override
  protected boolean isUseDefaultFilters() {
    return this.annotation.useDefaultFilters();
  }

  @Override
  protected Set<Class<?>> getDefaultIncludes() {
    return Collections.emptySet();
  }

  @Override
  protected Set<Class<?>> getComponentIncludes() {
    return Collections.emptySet();
  }

}
