/*
 *    Copyright 2009-2025 the original author or authors.
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
package org.apache.ibatis.executor.result;

import org.apache.ibatis.exceptions.PersistenceException;

/**
 * @author Ryan Lamore
 */
public class ResultMapException extends PersistenceException {
  private static final long serialVersionUID = 3270932060569707623L;

  public ResultMapException() {
  }

  public ResultMapException(String message) {
    super(message);
  }

  public ResultMapException(String message, Throwable cause) {
    super(message, cause);
  }

  public ResultMapException(Throwable cause) {
    super(cause);
  }
}
