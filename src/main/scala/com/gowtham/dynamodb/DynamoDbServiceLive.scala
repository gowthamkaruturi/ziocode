package com.gowtham.dynamodb

import zio.dynamodb.DynamoDBQuery.scanAllItem
import zio.{ZIO, stream}
import zio.dynamodb.{DynamoDBExecutor, Item}

case class DynamoDbServiceLive() extends DynamoDbService {
  override def getAllItems (tableName: String): ZIO[DynamoDBExecutor,Throwable, stream.Stream[Throwable,Item]] = {
    val response = for {
      getAllResponse <- scanAllItem(tableName = tableName).execute
    } yield (getAllResponse)
    response
  }

}
