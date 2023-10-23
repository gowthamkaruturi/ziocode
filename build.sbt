ThisBuild / scalaVersion := "2.13.12"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.example"
ThisBuild / organizationName := "example"

val V = new {
  val sttpcore = "1.5.5"
  val tapir = "1.0.0"
  val zio = "1.0.18"
  val zioConfig = "1.0.10"
  val zioCats = "2.5.1.0"
  val zioPrelude = "1.0.0-RC8"
  val circeVersion = "0.12.3"

}
lazy val root = (project in file("."))
  .settings(
    name := "Ziocode",
    libraryDependencies ++= Seq(
      "com.softwaremill.magnolia1_2" %% "magnolia" % "1.1.2",
      "com.softwaremill.sttp.model" %% "core" % "1.5.5",
      "com.softwaremill.sttp.shared" %% "zio1" % "1.3.13",
      "com.softwaremill.sttp.tapir" %% "tapir-server" % "1.0.0",
      "com.softwaremill.sttp.tapir" %% "tapir-zio1-http-server" % "1.0.0",
      "com.softwaremill.sttp.tapir" %% "tapir-zio1" % "1.0.0",
     "com.softwaremill.sttp.tapir" %% "tapir-json-circe" % "1.0.0",
      "dev.zio" %% "zio" % "1.0.18",
      "dev.zio" %% "zio-config" % "1.0.10",
      "dev.zio" %% "zio-config-typesafe" % "1.0.10",
      "dev.zio" %% "zio-config-refined" % "1.0.10",
      "dev.zio" %% "zio-config-magnolia" % "1.0.10",
      "io.d11" %% "zhttp" % "1.0.0.0-RC29",
      //"io.circe" %%% "circe-generic" % Versions.circe,
      //"dev.zio" %% "zio-interop-cats" %" 2.5.1.0",
      "dev.zio" %% "zio-json" % "0.6.2",
      "dev.zio" %% "zio-prelude" % V.zioPrelude,
     // "dev.zio" %% "zio.streams" % V.zio,

    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
