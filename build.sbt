ThisBuild / scalaVersion := "2.13.12"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

val tapirVersion = "1.0.2"
val ZIOVersion = "2.0.1"
val ZIOConfigVersion = "3.0.1"

lazy val root = (project in file("."))
  .settings(
    name := "Ziocode",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % tapirVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-zio" % tapirVersion,
      "io.d11" %% "zhttp" % "2.0.0-RC10",
      "dev.zio" %% "zio" % ZIOVersion,
      "dev.zio" %% "zio-config" % ZIOConfigVersion,
      "dev.zio" %% "zio-config-typesafe" % ZIOConfigVersion,
      "dev.zio" %% "zio-config-magnolia" % ZIOConfigVersion,
      "dev.zio" %% "zio-dynamodb" % "0.2.12",
      "dev.zio" %% "zio-logging" % "2.0.0",
      "dev.zio" %% "zio-logging-slf4j" % "2.0.0",
      "org.apache.logging.log4j" % "log4j-api" % "2.20.0",
      "org.apache.logging.log4j" % "log4j-core" % "2.20.0",
      "org.apache.logging.log4j" % "log4j-slf4j2-impl" % "2.20.0",
      "org.slf4j" % "slf4j-simple"% "2.0.9"
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
