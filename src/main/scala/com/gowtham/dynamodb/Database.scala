package com.gowtham.dynamodb

import com.gowtham.models.{Customer, Customers, Order, Orders, Product, Products}

import scala.collection.mutable.ListBuffer

object Database {

  var customerList: Customers = Customers(ListBuffer(
    Customer(customerId = 1, firstName = "gowtham", lastName = "karuturi", address = "5613 winona ln mckinney tx", email = "test1@gmail.com"),
    Customer(customerId = 2, firstName = "nagarjuna", lastName = "karuturi", address = "34655 skylark dr union city", email = "test2@gmail.com"),
    Customer(customerId = 3, firstName = "abhiram", lastName = "vemuri", address = "1828 harrongton way leander tx", email = "test3@gmail.com")
  ))
  var OrderList: Orders = Orders(ListBuffer(
    Order(orderId = 1, customerId = 3, products = List[1,2], totalPrice = 2.00),
    Order(orderId = 2, customerId = 1, products = List[1], totalPrice = 1.00)

  ))
  var productList: Products = Products(ListBuffer(
    Product(productId = 1, productName = "pen", price = 1.00),
    Product(productId = 2, productName = "pencil", price = 1.00)
  ))
}
