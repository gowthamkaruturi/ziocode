package com.gowtham

import com.gowtham.config.AppConfig
import zio._
import zio.config._
import zio.config.typesafe.TypesafeConfig
import zio.console._

object Main extends App {
  override def run(args: List[String]): URIO[ZEnv, ExitCode] = {

    

    val program: ZIO[Has[AppConfig] with Console, Nothing, Unit] = for {
      config <- ZIO.service[AppConfig]
      _ <- println(s"http host: ${config.httpConfig.host}")
    } yield ()

    program.provideCustomLayer().exitCode
  }
}
