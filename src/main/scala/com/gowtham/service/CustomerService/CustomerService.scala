package com.gowtham.service.CustomerService

import com.gowtham.dynamodb.{Database, DynamoDbService}
import com.gowtham.exception.CustomerError
import com.gowtham.models.{Customer, Customers}
import zio.dynamodb.DynamoDBExecutor
import zio.{ZIO, ZLayer}

trait CustomerService {
  def getAllCustomer:  ZIO[Any, CustomerError.NotFound, List[Customer] ]
//  def createCustomer(customer:Customer) : ZIO[Any, CustomerError.InvalidInput, Customer ]
//  def updateCustomer(customer:Customer) : ZIO[Any, CustomerError.InvalidInput, Customer ]
//  def deleteCustomer(customerId:Int) : ZIO[Any, CustomerError.InvalidInput, Unit ]
//  def customerById(customerId:Int) :ZIO[Any, CustomerError.NotFound, Customer ]
}


object CustomerService {
  lazy val live: ZLayer[DynamoDbService, Nothing, CustomerService] = ZLayer {
    for {
      dynamodbService <- ZIO.service[DynamoDbService]
    } yield CustomerServiceLive(dynamodbService)
  }


}    


