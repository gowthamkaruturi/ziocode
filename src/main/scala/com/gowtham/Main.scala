package com.gowtham

import com.gowtham.api.HttpServer
import com.gowtham.app.App
import com.gowtham.config.AppConfig
import com.gowtham.dynamodb.DynamoDbLocal.dynamoDBExecutorLayer
import com.gowtham.dynamodb.DynamoDbService
import com.gowtham.service.CustomerService.CustomerService
import zio._



object Main extends ZIOAppDefault {
  override def run: ZIO[Any with ZIOAppArgs with Scope, Any, Any] = {
    App.server.provide(
      HttpServer.live,
      CustomerService.live,
      DynamoDbService.live,
      dynamoDBExecutorLayer,
      AppConfig.live,
      //ZLayer.Debug.tree
    )
  }

}
