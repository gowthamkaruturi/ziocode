package com.gowtham.config

import zio._
import zio.config._
import zio.config.magnolia._
import zio.config.syntax._
import zio.config.typesafe.TypesafeConfigSource
import zio.config.typesafe.TypesafeConfigSource.fromTypesafeConfig

import java.io.File



final case class AppConfig(dynamodbconfig: Dynamodbconfig, httpConfig: HttpConfig)
case class Dynamodbconfig(endpoint: String, region:String)
final case class HttpConfig(host:String , port :Int)


object AppConfig {
  val live: ZLayer[Any, ReadError[String], AppConfig] =
    ZLayer {
      read {
        descriptor[AppConfig].from(
          TypesafeConfigSource.fromHoconFile(file = new File("./applicaiton.conf"))
        )
      }
    }
}