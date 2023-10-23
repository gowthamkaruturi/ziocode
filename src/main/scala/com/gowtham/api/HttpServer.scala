package com.gowtham.api

import com.gowtham.config.HttpConfig
import com.gowtham.dynamodb.Database
import com.gowtham.models.Customer
import com.gowtham.service.CustomerService.CustomerService
import sttp.tapir._
import sttp.tapir.json.circe._
import zhttp.http.HttpApp
import zio.{ExitCode, Task, ZIO, ZLayer}

trait HttpServer {
  def httpRoutes: ZIO[Any, Nothing, HttpApp[Any, Throwable]]
}

object HttpServer {

  lazy val live: ZLayer[CustomerService, Nothing, HttpServer] = ZLayer {
    for {
      customerService <- ZIO.service[CustomerService]
    } yield new HttpServerLive(customerService)
  }

  def httpRoutes: ZIO[HttpServer, Nothing, HttpApp[Any, Throwable]] =
    ZIO.serviceWithZIO[HttpServer](_.httpRoutes)

}
