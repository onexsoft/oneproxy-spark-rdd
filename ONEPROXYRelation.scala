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

import java.util.Properties

import scala.collection.mutable.ArrayBuffer

import org.apache.spark.internal.Logging
import org.apache.spark.Partition
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession, SQLContext}
import org.apache.spark.sql.sources._
import org.apache.spark.sql.types.StructType

/**
 * Instructions on how to partition the table among workers.
 */
private[sql] object ONEPROXYRelation extends Logging {

}

private[sql] case class ONEPROXYRelation(
    url: String,
    table: String,
    properties: Properties = new Properties())(@transient val sparkSession: SparkSession)
  extends BaseRelation
  with PrunedFilteredScan
  with InsertableRelation {

  override def sqlContext: SQLContext = sparkSession.sqlContext

  override val needConversion: Boolean = false

  override val schema: StructType = JDBCRDD.resolveTable(url, table, properties)

  // Check if ONEPROXYRDD.compileFilter can accept input filters
  override def unhandledFilters(filters: Array[Filter]): Array[Filter] = {
    filters.filter(ONEPROXYRDD.compileFilter(_).isEmpty)
  }

  override def buildScan(requiredColumns: Array[String], filters: Array[Filter]): RDD[Row] = {
    // Rely on a type erasure hack to pass RDD[InternalRow] back as RDD[Row]
    ONEPROXYRDD.scanTable(
      sparkSession.sparkContext,
      schema,
      url,
      properties,
      table,
      requiredColumns,
      filters).asInstanceOf[RDD[Row]]
  }

  override def insert(data: DataFrame, overwrite: Boolean): Unit = {
    // Disable data insert for OneProxy data source
    //data.write
    //  .mode(if (overwrite) SaveMode.Overwrite else SaveMode.Append)
    //  .jdbc(url, table, properties)
  }

  override def toString: String = {
    // credentials should not be included in the plan output, table information is sufficient.
    s"ONEPROXYRelation(${table})"
  }
}
