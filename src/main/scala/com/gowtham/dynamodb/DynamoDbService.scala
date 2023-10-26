package com.gowtham.dynamodb

import zio._
import zio.dynamodb._



  trait DynamoDbService {
    def getAllItems(tableName: String): ZIO[DynamoDBExecutor,Throwable, stream.Stream[Throwable,Item]]


  }

  object DynamoDbService {
    lazy val live: ZLayer[DynamoDBExecutor, Nothing, DynamoDbService] = ZLayer {
      ZIO.succeed (DynamoDbServiceLive())

    }
  }

