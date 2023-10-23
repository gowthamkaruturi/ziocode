package com.gowtham.api

import com.gowtham.exception
import com.gowtham.exception.CustomerError
import com.gowtham.models.Customer
import com.gowtham.service.CustomerService.CustomerService
import sttp.model.StatusCode
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.{Schema, oneOf, oneOfVariant, path}
import zhttp.http.{Http, HttpApp, Request, Response}
import sttp.tapir.ztapir._
import zio.ZIO
import zio.json.{DeriveJsonCodec, JsonCodec}
import sttp.tapir.server.ziohttp.ZioHttpInterpreter

class HttpServerLive(customerService: CustomerService) extends HttpServer{
  object InvalidInput {
    implicit lazy val codec: JsonCodec[exception.CustomerError.InvalidInput] = DeriveJsonCodec.gen
    implicit lazy val schema: Schema[exception.CustomerError.InvalidInput] = Schema.derived
  }

  object NotFound {
    implicit lazy val codec: JsonCodec[exception.CustomerError.NotFound] = DeriveJsonCodec.gen
    implicit lazy val schema: Schema[exception.CustomerError.NotFound] = Schema.derived
  }
  private val baseEndpoint = endpoint.in("api").in("v1")
  private val getCustomerErrorOut = oneOf[CustomerError](
    oneOfVariant(StatusCode.NotFound, jsonBody[CustomerError.NotFound].description("customer not found."))
  )

  private val getCustomerInputErrorOut = oneOf[CustomerError](
    oneOfVariant(StatusCode.BadRequest, jsonBody[CustomerError.InvalidInput].description("Invalid input."))
  )

  private val getCustomerById =
    baseEndpoint.get
      .in(path[Int]("id"))
      .out(Customer)
//  private val allRoutes: Http[Any, Throwable, Request, Response] = {
//    getCustomerById
//    ZioHttpInterpreter().toHttp(List(getCustomerById.(_ => vaccinationService.getAllVaccination()),
//  }

  override def httpRoutes: ZIO[Any, Nothing, HttpApp[Any, Throwable]] = ???
}