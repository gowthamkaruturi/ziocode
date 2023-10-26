package com.gowtham.api

import com.gowtham.service.CustomerService.CustomerService
import zhttp.http.HttpApp
import zio.{ExitCode, Task, ZIO, ZLayer}

trait HttpServer {
  def httpRoutes: ZIO[Any, Nothing, HttpApp[Any, Throwable]]
}

object HttpServer {

  lazy val live: ZLayer[CustomerService, Nothing, HttpServer] = ZLayer {
    for {
      customerService <- ZIO.service[CustomerService]
    } yield  HttpServerLive(customerService)
  }

  def httpRoutes: ZIO[HttpServer, Nothing, HttpApp[Any, Throwable]] =
    ZIO.serviceWithZIO[HttpServer](_.httpRoutes)

}
