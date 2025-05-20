/*
 * Copyright (c) 2025 pipiolo
 *
 * This code is licensed under the MIT License.
 * See the LICENSE file for more details.
 */

package com.pipiolo.blog.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.cql.AsyncResultSet

import scala.compat.java8.FutureConverters
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class CassandraPersonV1Repository(session: CqlSession) {
  private val insertQuery = s"INSERT INTO $KOREA.$PERSON_V1_TABLE ($ID, $NAME) VALUES (?, ?);"
  private val selectQuery = s"SELECT $NAME FROM $KOREA.$PERSON_V1_TABLE WHERE $ID = ?;"

  def set(id: String, name: String): Future[AsyncResultSet] = {
    val preparedStatement = session.prepare(insertQuery)
    val boundStatement    = preparedStatement.bind(id, name)

    FutureConverters.toScala(session.executeAsync(boundStatement))
  }

  def get(id: String): Future[String] = {
    val preparedStatement = session.prepare(selectQuery)
    val boundStatement    = preparedStatement.bind(id)

    FutureConverters.toScala(session.executeAsync(boundStatement)).map(_.one().getString(NAME))
  }
}
