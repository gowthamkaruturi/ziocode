package com.gowtham.models

import io.circe.generic.JsonCodec
import sttp.tapir.Schema
import zio.json.{DeriveJsonCodec, JsonCodec}

import scala.collection.mutable.ListBuffer


case class Customer(customerId: Int, firstName: String, lastName: String, address: String, email: String)
object Customer {
  implicit val jsonCodec: JsonCodec[Customer] = DeriveJsonCodec.gen
  implicit val schema: Schema[Customer] = Schema.derived
}

case class Order(orderId: Int, customerId: Int, products: List[String], totalPrice: Double)
object Order {
  implicit val jsonCodec: JsonCodec[Order] = DeriveJsonCodec.gen
  implicit val schema: Schema[Order] = Schema.derived
}

case class Product(productId: Int, productName: String, price: Double)

object Product {
  implicit val jsonCodec: JsonCodec[Product] = DeriveJsonCodec.gen
  implicit val schema: Schema[Product] = Schema.derived
}


case class Customers(customers: ListBuffer[Customer])
object Customers {
  implicit val jsonCodec: JsonCodec[Customers] = DeriveJsonCodec.gen
  implicit val schema: Schema[Customers] = Schema.derived
}

case class Orders(orders: ListBuffer[Order])
object Orders {
  implicit val jsonCodec: JsonCodec[Orders] = DeriveJsonCodec.gen
  implicit val schema: Schema[Orders] = Schema.derived
}

case class Products(products: ListBuffer[Product])

object Products {
  implicit val jsonCodec: JsonCodec[Products] = DeriveJsonCodec.gen
  implicit val schema: Schema[Products] = Schema.derived
}