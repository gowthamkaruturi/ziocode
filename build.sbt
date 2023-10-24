ThisBuild / scalaVersion := "2.13.12"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

val V = new {
  val sttpcore = "1.5.5"
  val tapir = "1.2.10"
  val zio = "2.0.13"
  val zioConfig = "3.0.7"
  val zioCats = "23.0.03"
  val zioPrelude = "1.0.0-RC8"
  val circeVersion = "0.12.3"

}
lazy val root = (project in file("."))
  .settings(
    name := "Ziocode",
    libraryDependencies ++= Seq(
      "com.softwaremill.sttp.model" %% "core" % "1.5.5",
      "com.softwaremill.sttp.shared" %% "zio1" % "1.3.13",
      "com.softwaremill.sttp.tapir" %% "tapir-server" % V.tapir,
      "com.softwaremill.sttp.tapir" %% "tapir-zio1-http-server" % V.tapir,
      "com.softwaremill.sttp.tapir" %% "tapir-zio" % V.tapir,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % V.tapir,
      "dev.zio" %% "zio" % V.zio,
      "dev.zio" %% "zio-config" % V.zioConfig,
      "dev.zio" %% "zio-config-typesafe" % V.zioConfig,
      "dev.zio" %% "zio-config-refined" % V.zioConfig,
      "dev.zio" %% "zio-config-magnolia" % V.zioConfig,
      "io.d11" %% "zhttp" % "2.0.0-RC11",
      "dev.zio" %% "zio-interop-cats" % V.zioCats,
      "dev.zio" %% "zio-json" % "0.6.2",
      "ch.qos.logback" %"logback-classic" %"1.4.7",
      "dev.zio" %% "zio-prelude" % V.zioPrelude

    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
