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
package org.mybatis.spring.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.AbstractMyBatisSpringTest;
import org.springframework.transaction.support.DefaultTransactionDefinition;

class SpringTransactionManagerTest extends AbstractMyBatisSpringTest {

  @Test
  void shouldNoOpWithTx() throws Exception {
    var txDef = new DefaultTransactionDefinition();
    txDef.setPropagationBehaviorName("PROPAGATION_REQUIRED");
    var status = txManager.getTransaction(txDef);

    var transactionFactory = new SpringManagedTransactionFactory();
    var transaction = (SpringManagedTransaction) transactionFactory.newTransaction(dataSource, null, false);
    transaction.getConnection();
    transaction.commit();
    transaction.close();
    assertThat(connection.getNumberCommits()).as("should not call commit on Connection").isEqualTo(0);
    assertThat(connection.isClosed()).as("should not close the Connection").isFalse();

    txManager.commit(status);
  }

  // TODO Test does not compile
  // @Disabled
  // @Test
  // public void shouldManageWithOtherDatasource() throws Exception {
  // DefaultTransactionDefinition txDef = new DefaultTransactionDefinition();
  // txDef.setPropagationBehaviorName("PROPAGATION_REQUIRED");
  // TransactionStatus status = txManager.getTransaction(txDef);
  //
  // SpringManagedTransactionFactory transactionFactory = new SpringManagedTransactionFactory(new MockDataSource());
  // SpringManagedTransaction transaction = (SpringManagedTransaction) transactionFactory.newTransaction(connection,
  // false);
  // transaction.commit();
  // transaction.close();
  // assertEquals(1, connection.getNumberCommits(), "should call commit on Connection");
  // assertTrue(connection.isClosed(), "should close the Connection");
  //
  // txManager.commit(status);
  // }

  @Test
  void shouldManageWithNoTx() throws Exception {
    var transactionFactory = new SpringManagedTransactionFactory();
    var transaction = (SpringManagedTransaction) transactionFactory.newTransaction(dataSource, null, false);
    transaction.getConnection();
    transaction.commit();
    transaction.close();
    assertThat(connection.getNumberCommits()).as("should call commit on Connection").isEqualTo(1);
    assertThat(connection.isClosed()).as("should close the Connection").isTrue();
  }

  @Test
  void shouldNotCommitWithNoTxAndAutocommitIsOn() throws Exception {
    var transactionFactory = new SpringManagedTransactionFactory();
    var transaction = (SpringManagedTransaction) transactionFactory.newTransaction(dataSource, null, false);
    connection.setAutoCommit(true);
    transaction.getConnection();
    transaction.commit();
    transaction.close();
    assertThat(connection.getNumberCommits()).as("should not call commit on a Connection with autocommit").isEqualTo(0);
    assertThat(connection.isClosed()).as("should close the Connection").isTrue();
  }

  @Test
  void shouldIgnoreAutocommit() throws Exception {
    var transactionFactory = new SpringManagedTransactionFactory();
    var transaction = (SpringManagedTransaction) transactionFactory.newTransaction(dataSource, null, true);
    transaction.getConnection();
    transaction.commit();
    transaction.close();
    assertThat(connection.getNumberCommits()).as("should call commit on Connection").isEqualTo(1);
    assertThat(connection.isClosed()).as("should close the Connection").isTrue();
  }

}
