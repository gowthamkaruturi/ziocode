package com.gowtham.api

import com.gowtham.exception
import com.gowtham.exception.CustomerError
import com.gowtham.models.Customer
import com.gowtham.service.CustomerService.CustomerService
import sttp.model.StatusCode
import sttp.tapir.json.zio.jsonBody
import sttp.tapir.ztapir._
import sttp.tapir.{oneOf, oneOfVariant}
import zhttp.http.{Http, HttpApp, Request, Response}
import zio._
import sttp.tapir.server.ziohttp.ZioHttpInterpreter




case class HttpServerLive(customerService: CustomerService) extends HttpServer{

  private val customerBaseEndpoint = endpoint.in("customers")
  private val getCustomerErrorOut = oneOf[CustomerError](
    oneOfVariant(
      StatusCode.NotFound,
      jsonBody[exception.CustomerError.NotFound].description("customer not found")
    )
  )
  private val getCustomerInputErrorOut = oneOf[CustomerError](
    oneOfVariant(
      StatusCode.BadRequest,
      jsonBody[exception.CustomerError.InvalidInput].description("invalid input ")
    )
  )

  private val getAllCustomersEndpoint = customerBaseEndpoint.get
    .errorOut(getCustomerErrorOut)
    .out(jsonBody[List[Customer]])

  private val allroutes: Http[Any, Throwable,Request,Response] = {
    ZioHttpInterpreter().toHttp(
      List(getAllCustomersEndpoint.zServerLogic(_ => customerService.getAllCustomer))
    )
  }
private val endpoints = {
  val endpoints = List(
    getAllCustomersEndpoint
  )
  endpoints.map(_.tags(List("customers Endpoint")))
}
  override def httpRoutes: ZIO[Any, Nothing, HttpApp[Any, Throwable]] =
    for {
      routesHttp <- ZIO.succeed(allroutes)

    } yield (routesHttp )
}

