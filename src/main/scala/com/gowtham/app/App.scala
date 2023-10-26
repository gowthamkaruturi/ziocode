package com.gowtham.app

import com.gowtham.api.HttpServer
import com.gowtham.config.AppConfig
import zio._
import zhttp.service.Server

object App {

  def server = ZIO.scoped {
    for {
      config  <- ZIO.service[AppConfig]
      httpApp <- HttpServer.httpRoutes
      start <-  Server.app(httpApp).withBinding(config.http.host, config.http.port).startDefault
    _ <- ZIO.logInfo(s"Server started on port: ${start}")
      _ <- ZIO.never
    } yield ()
  }
}
