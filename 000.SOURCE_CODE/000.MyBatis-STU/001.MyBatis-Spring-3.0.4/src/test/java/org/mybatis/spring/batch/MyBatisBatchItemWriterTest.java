/*
 * Copyright 2010-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.spring.batch;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.executor.BatchResult;
import org.apache.ibatis.session.ExecutorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.domain.Employee;
import org.springframework.batch.item.Chunk;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

/**
 * @author Putthiphong Boonphong
 */
class MyBatisBatchItemWriterTest {

  @Mock
  private SqlSessionTemplate mockSqlSessionTemplate;

  @InjectMocks
  private MyBatisBatchItemWriter<Employee> writer;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testZeroBatchResultShouldThrowException() {
    Chunk<Employee> employees = Chunk.of(new Employee(), new Employee());
    List<BatchResult> batchResults = Collections.emptyList();

    given(mockSqlSessionTemplate.flushStatements()).willReturn(batchResults);

    assertThrows(InvalidDataAccessResourceUsageException.class, () -> writer.write(employees));
  }

  @Test
  void testZeroUpdateCountShouldThrowException() {
    Chunk<Employee> employees = Chunk.of(new Employee(), new Employee());

    var batchResult = new BatchResult(null, null);
    batchResult.setUpdateCounts(new int[] { 1, 0 });
    List<BatchResult> batchResults = Collections.singletonList(batchResult);

    given(mockSqlSessionTemplate.flushStatements()).willReturn(batchResults);

    assertThrows(EmptyResultDataAccessException.class, () -> writer.write(employees));
  }

  @Test
  void testItemToParameterConverterIsDefault() {
    this.writer.setAssertUpdates(false);
    this.writer.setStatementId("updateEmployee");

    var employee = new Employee();
    Chunk<Employee> employees = Chunk.of(employee);
    writer.write(employees);

    Mockito.verify(this.mockSqlSessionTemplate).update("updateEmployee", employee);
  }

  @Test
  void testSetItemToParameterConverter() {
    this.writer.setAssertUpdates(false);
    this.writer.setStatementId("updateEmployee");
    this.writer.setItemToParameterConverter(item -> {
      Map<String, Object> parameter = new HashMap<>();
      parameter.put("item", item);
      parameter.put("now", LocalDateTime.now(Clock.fixed(Instant.ofEpochMilli(0), ZoneId.systemDefault())));
      return parameter;
    });

    var employee = new Employee();
    Chunk<Employee> employees = Chunk.of(employee);
    writer.write(employees);

    Map<String, Object> parameter = new HashMap<>();
    parameter.put("item", employee);
    parameter.put("now", LocalDateTime.now(Clock.fixed(Instant.ofEpochMilli(0), ZoneId.systemDefault())));
    Mockito.verify(this.mockSqlSessionTemplate).update("updateEmployee", parameter);
  }

  @Test
  void testItemToParameterConverterIsNull() {
    given(mockSqlSessionTemplate.getExecutorType()).willReturn(ExecutorType.BATCH);
    this.writer.setStatementId("updateEmployee");
    writer.setItemToParameterConverter(null);

    assertThrows(IllegalArgumentException.class, () -> writer.afterPropertiesSet(),
        "A itemToParameterConverter is required.");

  }

}
