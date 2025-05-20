/*
 * Copyright (c) 2025 pipiolo
 *
 * This code is licensed under the MIT License.
 * See the LICENSE file for more details.
 */

package com.pipiolo.blog.cassandra

import com.datastax.oss.driver.api.core.CqlSession
import com.dimafeng.testcontainers.CassandraContainer
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.testcontainers.utility.DockerImageName

import java.util.UUID
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class CassandraPersonV1RepositoryTest extends AnyFlatSpec with Matchers with BeforeAndAfterAll with TestContainerForAll {
  override val containerDef: CassandraContainer.Def = CassandraContainer.Def(dockerImageName = DockerImageName.parse("cassandra:5.0.3"))

  override def afterContainersStart(container: CassandraContainer): Unit = {
    super.afterContainersStart(container)

    val session = CqlSession
      .builder()
      .addContactPoint(container.cassandraContainer.getContactPoint)
      .withLocalDatacenter(container.cassandraContainer.getLocalDatacenter)
      .build()

    session.execute(s"CREATE KEYSPACE IF NOT EXISTS $KOREA WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };")
    session.execute(s"CREATE TABLE IF NOT EXISTS $KOREA.$PERSON_V1_TABLE (id text PRIMARY KEY, name text);")
  }

  it should "데이터를 저장 및 조회할 수 있다." in withContainers { cassandraContainer =>
    val session = CqlSession
      .builder()
      .addContactPoint(cassandraContainer.cassandraContainer.getContactPoint)
      .withLocalDatacenter(cassandraContainer.cassandraContainer.getLocalDatacenter)
      .withKeyspace(KOREA)
      .build()

    val personV1Repository = new CassandraPersonV1Repository(session)

    val id   = UUID.randomUUID().toString
    val name = "pipiolo"

    1 shouldBe 1

    Await.result(personV1Repository.set(id = id, name = name), 5.seconds)
    Await.result(personV1Repository.get(id = id), 5.seconds) shouldBe name
  }
}
