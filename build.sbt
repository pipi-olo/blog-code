import scala.collection.Seq

ThisBuild / version      := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.12.20"
ThisBuild / organization := "com.pipiolo"

lazy val versions = new {
  val twitterLib = "24.2.0"

  val logback   = "1.5.18"
  val scalaTest = "3.2.19"
}

lazy val commonSettings = Seq(
  scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked", "-encoding", "UTF-8"),
  publish / skip := true
)

lazy val root = (project in file("."))
  .settings(commonSettings *)
  .settings(
    name := "blog-code"
  )
