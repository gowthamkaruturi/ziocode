package com.gowtham.config

import zio._
import zio.config._
import zio.config.magnolia.{descriptor, _}
import zio.config.typesafe.TypesafeConfigSource

import java.io.File

final case class AppConfig(
    dynamodb: Dynamodbconfig,
    http: HttpConfig
)
case class Dynamodbconfig(endpoint: String, region: String)
final case class HttpConfig(host: String, port: Int)

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
