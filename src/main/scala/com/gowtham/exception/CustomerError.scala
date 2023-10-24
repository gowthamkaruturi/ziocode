package com.gowtham.exception
import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder
import sttp.tapir.Schema
import zio.json.{DeriveJsonCodec, DeriveJsonDecoder, DeriveJsonEncoder, JsonCodec, JsonDecoder, JsonEncoder}
sealed trait CustomerError

object CustomerError {
  implicit lazy val codec: JsonCodec[CustomerError] = DeriveJsonCodec.gen

  case class InvalidInput(error: String) extends CustomerError

  object InvalidInput {
    implicit lazy val codec: JsonCodec[InvalidInput] = DeriveJsonCodec.gen
    implicit lazy val schema: Schema[InvalidInput] = Schema.derived
    implicit val decoder :JsonDecoder[InvalidInput] =DeriveJsonDecoder.gen[InvalidInput]
    implicit val encoder :JsonEncoder[InvalidInput] =DeriveJsonEncoder.gen[InvalidInput]
  }
  case class NotFound(message: String) extends CustomerError

  object NotFound  {
    implicit lazy val codec: JsonCodec[NotFound] = DeriveJsonCodec.gen
    implicit lazy val schema: Schema[NotFound] = Schema.derived
    implicit val decoder: JsonDecoder[NotFound] = DeriveJsonDecoder.gen[NotFound]
    implicit val encoder: JsonEncoder[NotFound] = DeriveJsonEncoder.gen[NotFound]
   
  }
}



