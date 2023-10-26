package com.gowtham.dynamodb

import com.gowtham.models.{Customer, Customers, Order, Orders, Product, Products}
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import zio.ZLayer
import zio.aws.core.config
import zio.aws.dynamodb
import zio.aws.dynamodb.DynamoDb
import zio.aws.netty
import zio.dynamodb.DynamoDBExecutor

import java.net.URI


object Database {

  var customerList =List(
    Customer(customerId = 1, firstName = "gowtham", lastName = "karuturi", address = "5613 winona ln mckinney tx", email = "test1@gmail.com"),
    Customer(customerId = 2, firstName = "nagarjuna", lastName = "karuturi", address = "34655 skylark dr union city", email = "test2@gmail.com"),
    Customer(customerId = 3, firstName = "abhiram", lastName = "vemuri", address = "1828 harrongton way leander tx", email = "test3@gmail.com")
  )
  var OrderList = List(
    Order(orderId = 1, customerId = 3, products = List(1,2), totalPrice = 2.00),
    Order(orderId = 2, customerId = 1, products = List(1), totalPrice = 1.00)

  )
  var productList =List(
    Product(productId = 1, productName = "pen", price = 1.00),
    Product(productId = 2, productName = "pencil", price = 1.00)
  )
}

object DynamoDbLocal {
  val awsConfig = ZLayer.succeed(
    config.CommonAwsConfig(
      region = None,
      credentialsProvider = StaticCredentialsProvider.create(AwsBasicCredentials.create("dummy", "dummy")),
      endpointOverride = None,
      commonClientConfig = None
    )
  )

  val dynamoDbLayer: ZLayer[Any, Throwable, DynamoDb] =
    (netty.NettyHttpClient.default ++ awsConfig) >>> config.AwsConfig.default >>> dynamodb.DynamoDb.customized {
      builder =>
        builder.endpointOverride(URI.create("http://localhost:8000")).region(Region.US_EAST_1)
    }

  val dynamoDBExecutorLayer = dynamoDbLayer >>> DynamoDBExecutor.live
}
