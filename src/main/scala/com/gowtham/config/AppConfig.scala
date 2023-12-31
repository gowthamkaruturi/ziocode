package com.gowtham.config

import zio._
import zio.config._
import zio.config.magnolia.{descriptor, _}
import zio.config.typesafe.TypesafeConfigSource

import java.io.File

final case class AppConfig(
    dynamodb: Dynamodb,
    http: Http
)
case class Dynamodb(endpoint: String, region: String)
case class Http(host: String, port: Int)

object AppConfig {
  val live: ZLayer[Any, ReadError[String], AppConfig] =
    ZLayer {
      read {
        descriptor[AppConfig].from(
          TypesafeConfigSource.fromResourcePath
            .at(PropertyTreePath.$("config"))
        )
      }
    }
}
