package com.gowtham.models

import io.circe.generic.auto._
import sttp.tapir.json.circe._

import scala.collection.mutable.ListBuffer
import zio.json.{DeriveJsonCodec, DeriveJsonDecoder, DeriveJsonEncoder, JsonCodec, JsonDecoder, JsonEncoder}
import sttp.tapir.Schema
import io.circe.Encoder


case class Customer(customerId: Int, firstName: String, lastName: String, address: String, email: String)
object Customer {
  implicit val jsonCodec: JsonCodec[Customer] = DeriveJsonCodec.gen
  implicit val schema: Schema[Customer] = Schema.derived
  implicit val decoder: JsonDecoder[Customer] = DeriveJsonDecoder.gen[Customer]
  implicit val encoder: JsonEncoder[Customer] = DeriveJsonEncoder.gen[Customer]
}

case class Order(orderId: Int, customerId: Int, products: List[Int], totalPrice: Double)
object Order {
  implicit val jsonCodec: JsonCodec[Order] = DeriveJsonCodec.gen
  implicit val schema: Schema[Order] = Schema.derived
  implicit val decoder: JsonDecoder[Order] = DeriveJsonDecoder.gen[Order]
  implicit val encoder: JsonEncoder[Order] = DeriveJsonEncoder.gen[Order]
}

case class Product(productId: Int, productName: String, price: Double)

object Product {
  implicit val jsonCodec: JsonCodec[Product] = DeriveJsonCodec.gen
  implicit val schema: Schema[Product] = Schema.derived
  implicit val decoder: JsonDecoder[Product] = DeriveJsonDecoder.gen[Product]
  implicit val encoder: JsonEncoder[Product] = DeriveJsonEncoder.gen[Product]
}


case class Customers(customers: ListBuffer[Customer])
object Customers {
 // implicit val jsonCodec: JsonCodec[Customers] = DeriveJsonCodec.gen
  implicit val schema: Schema[Customers] = Schema.derived
 // implicit val decoder: JsonDecoder[Customers] = DeriveJsonDecoder.gen[Customers]
  implicit val encoder: JsonEncoder[Customers] = DeriveJsonEncoder.gen[Customers]
}

case class Orders(orders: ListBuffer[Order])
object Orders {
 // implicit val jsonCodec: JsonCodec[Orders] = DeriveJsonCodec.gen
  implicit val schema: Schema[Orders] = Schema.derived
  //implicit val decoder: JsonDecoder[Orders] = DeriveJsonDecoder.gen[Orders]
  implicit val encoder: JsonEncoder[Orders] = DeriveJsonEncoder.gen[Orders]
}

case class Products(products: ListBuffer[Product])

object Products {
//  implicit val jsonCodec: JsonCodec[Products] = DeriveJsonCodec.gen
  implicit val schema: Schema[Products] = Schema.derived
 // implicit val decoder: JsonDecoder[Products] = DeriveJsonDecoder.gen[Products]
  implicit val encoder: JsonEncoder[Products] = DeriveJsonEncoder.gen[Products]
}