package com.gowtham.api

import com.gowtham.exception
import com.gowtham.exception.CustomerError
import com.gowtham.models.Customer
import com.gowtham.service.CustomerService.CustomerService
import sttp.model.StatusCode
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.ztapir._
import sttp.tapir.{oneOf, oneOfVariant}
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import zhttp.http.{Http, HttpApp, Request, Response}
import zio._
import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec
import sttp.apispec.openapi.circe.yaml.RichOpenAPI
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import sttp.tapir.swagger.SwaggerUI




class HttpServerLive(customerService: CustomerService) extends HttpServer{

  private val customerBaseEndpoint = endpoint.in("api").in("v1").in("customers")
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

  private val getCustomerByIdEndpoint = customerBaseEndpoint.get
    .in(path[Int]( "id"))
    .errorOut(getCustomerErrorOut)
    .out(jsonBody[Customer])

  private val allroutes: Http[Any, Throwable,Request,Response] = {
    ZioHttpInterpreter().toHttp(
      List(getCustomerByIdEndpoint.zServerLogic(id => customerService.customerById(customerId = id)))
    )
  }
private val endpoints = {
  val endpoints = List(
    getCustomerByIdEndpoint
  )
  endpoints.map(_.tags(List("customers Endpoint")))
}
  override def httpRoutes: ZIO[Any, Nothing, HttpApp[Any, Throwable]] =
    for {
      openApi <- ZIO.succeed(OpenAPIDocsInterpreter().toOpenAPI(endpoints,"customer service", "0.1"))
      routesHttp <- ZIO.succeed(allroutes)
      endPointsHttp <- ZIO.succeed(ZioHttpInterpreter().toHttp(SwaggerUI[Task](openApi.toYaml)))

    } yield (routesHttp ++ endPointsHttp)
}

