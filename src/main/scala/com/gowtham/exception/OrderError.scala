package com.gowtham.exception

import sttp.tapir.Schema
import zio.json.{DeriveJsonCodec, JsonCodec}

sealed trait OrderError

object OrderError {
  implicit lazy val codec: JsonCodec[OrderError] = DeriveJsonCodec.gen

  case class InvalidInput(error: String) extends OrderError

  object InvalidInput {
    implicit lazy val codec: JsonCodec[InvalidInput] = DeriveJsonCodec.gen
    implicit lazy val schema: Schema[InvalidInput] = Schema.derived
  }

  case class NotFound(message: String) extends OrderError

  object NotFound {
    implicit lazy val codec: JsonCodec[NotFound] = DeriveJsonCodec.gen
    implicit lazy val schema: Schema[NotFound] = Schema.derived
  }
}
