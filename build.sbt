ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "test_sbt"
  )

val circeVersion = "0.14.3"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies +=  "org.rogach" %% "scallop" % "4.1.0"
libraryDependencies += "io.scalaland" %% "chimney" % "0.6.2"

dependencyOverrides += "log4j" % "log4j" % "1.2.16"

//addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.10.0-RC1")

