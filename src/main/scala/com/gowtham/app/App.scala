package com.gowtham.app

import com.gowtham.api.HttpServer
import com.gowtham.config.AppConfig
import zio.ZIO
import zhttp.service.Server

object App {

  def server: ZIO[AppConfig with HttpServer, Throwable, Nothing] = ZIO.scoped {
    for {
      config  <- ZIO.service[AppConfig]
      httpApp <- HttpServer.httpRoutes
      start <-  Server.app(httpApp).withBinding(config.httpConfig.host, config.httpConfig.port).start
      _ <- ZIO.logInfo(s"Server started on port: ${start}")
      _ <- ZIO.never
    } yield ()
  }
}
