import scala.collection.Seq

ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.20"
ThisBuild / organization := "com.pipiolo"

lazy val versions = new {
  val twitter = "24.2.0"

  val cassandra     = "4.19.0"
  val cassandraUnit = "4.3.1.0"

  val logback          = "1.5.18"
  val scalaJava8Compat = "1.0.2"
  val scalaTest        = "3.2.19"
}

lazy val commonSettings = Seq(
  scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-encoding", "UTF-8"),
  publish / skip := true
)

lazy val root = (project in file("."))
  .aggregate(devops)
  .settings(commonSettings *)
  .settings(
    name := "blog-code"
  )

// Devops
lazy val devops = project
  .in(file("devops"))
  .aggregate(cassandra)
  .settings(commonSettings *)

lazy val cassandra = project
  .in(file("devops/cassandra"))
  .aggregate(cassandraQuery)
  .settings(commonSettings *)

lazy val cassandraQuery = project
  .in(file("devops/cassandra/how-to-query-cassandra-using-datastax"))
  .settings(commonSettings *)
  .settings(
    name := "how-to-query-cassandra-using-datastax",
    libraryDependencies ++= Seq(
      "org.apache.cassandra"    % "java-driver-core"               % versions.cassandra,
      "org.apache.cassandra"    % "java-driver-query-builder"      % versions.cassandra,
      "org.apache.cassandra"    % "java-driver-mapper-runtime"     % versions.cassandra,
      "org.scala-lang.modules" %% "scala-java8-compat"             % versions.scalaJava8Compat,
      "org.scalatest"          %% "scalatest"                      % versions.scalaTest     % Test,
      "org.cassandraunit"       % "cassandra-unit"                 % versions.cassandraUnit % Test,
      "com.dimafeng"           %% "testcontainers-scala-scalatest" % "0.43.0"               % Test,
      "com.dimafeng"           %% "testcontainers-scala-cassandra" % "0.43.0"               % Test
    )
  )
