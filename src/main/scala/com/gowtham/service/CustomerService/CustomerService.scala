package com.gowtham.service.CustomerService

import com.gowtham.dynamodb.Database
import com.gowtham.exception.CustomerError
import com.gowtham.models.{Customer, Customers}
import zio.{ZIO, ZLayer}

trait CustomerService {

  def createCustomer(customer:Customer) : ZIO[Any, CustomerError.InvalidInput, Customers ]
  def updateCustomer(customer:Customer) : ZIO[Any, CustomerError.InvalidInput, Customers ]
  def deleteCustomer(customerId:Int) : ZIO[Any, CustomerError.InvalidInput, Unit ]
  def customerById(customerId:Int) :ZIO[Any, CustomerError.NotFound, Customer ]
}


object CustomerService {
  lazy val live: ZLayer[Any, Nothing, CustomerService] = ZLayer {
    ZIO.succeed( CustomerServiceLive())
  }


}    


