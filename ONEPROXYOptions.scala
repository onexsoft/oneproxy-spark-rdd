/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.sql.execution.datasources.jdbc

/**
 * Options for the OneProxy data source.
 */
private[jdbc] class ONEPROXYOptions(
    @transient private val parameters: Map[String, String])
  extends Serializable {

  // a JDBC URL
  val url = parameters.getOrElse("url", sys.error("Option 'url' not specified"))
  // name of table
  val table = parameters.getOrElse("dbtable", sys.error("Option 'dbtable' not specified"))
  // the where condition
  val where = parameters.getOrElse("where", null)
}
