package com.gowtham.service.CustomerService


import com.gowtham.dynamodb.DynamoDbLocal.dynamoDBExecutorLayer
import com.gowtham.dynamodb.{Database, DynamoDbService}
import com.gowtham.exception.CustomerError
import com.gowtham.models.{Customer, Customers}
import zio._
import zio.dynamodb.{DynamoDBExecutor, Item}

import scala.collection.mutable.ListBuffer

case class CustomerServiceLive(dynamoDbService: DynamoDbService) extends CustomerService{
  val customersTableName = "customers"
  val customerList = ListBuffer(Customer(customerId = 1, firstName = "gowtham", lastName = "karuturi", address = "5613 winona ln mckinney tx", email = "test1@gmail.com"),
    Customer(customerId = 2, firstName = "nagarjuna", lastName = "karuturi", address = "34655 skylark dr union city", email = "test2@gmail.com"),
    Customer(customerId = 3, firstName = "abhiram", lastName = "vemuri", address = "1828 harrongton way leander tx", email = "test3@gmail.com")
  )
//  override def createCustomer(customer: Customer): ZIO[Any, CustomerError.InvalidInput, Customer] = {
//    Database.customerList.find(customerDetails => customerDetails.customerId.equals(customer.customerId)) match {
//      case None =>
//
//        customerList += customer
//        Database.customerList = customerList.toList
//        ZIO.logInfo(s"created customer inside the repo ${customer.customerId}") *>
//        ZIO.succeed(customer)
//
//      case Some(customerInfo) =>
//        ZIO.logInfo(s"new customer is not added. Already exist same vaccinationId : ${customerInfo.customerId}") *>
//          ZIO.fail(CustomerError.InvalidInput(s"Insert is failed. Vaccination Id is already available ${customerInfo.customerId}"))
//    }
//  }
//
//  override def updateCustomer(customer: Customer): ZIO[Any, CustomerError.InvalidInput, Customer] = {
//    Database.customerList.find(customerDetails => customerDetails.customerId.equals(customer.customerId)) match {
//
//      case Some(customerInfo) =>
//        customerList.update(customerList.indexOf(customerInfo), customer)
//        Database.customerList = customerList.toList
//
//        ZIO.logInfo(s"Update customer for customer : ${customerInfo.customerId}") *>
//          ZIO.succeed(customer)
//
//      case _ =>
//        customerList += customer
//        ZIO.logInfo(s"Update customer for customerId : ${customer.customerId}") *>
//          ZIO.fail(CustomerError.InvalidInput(s"Update is failed. customer Id is not available ${customer.customerId}"))
//
//    }
//  }
//
//  override def deleteCustomer(customerId: Int): ZIO[Any, CustomerError.InvalidInput, Unit] =
//    customerList.find(customerDetails => customerDetails.customerId.equals()) match {
//      case Some(customerInfo) =>
//        customerList  -= customerInfo
//        Database.customerList = customerList.toList
//        ZIO.logInfo(s"Deleted customer  : $customerId") *>
//          ZIO.succeed(())
//      case _ =>
//        ZIO.logInfo(s"new customer couldn't be deleted. customer does not exist : $customerId") *>
//          ZIO.fail(CustomerError.InvalidInput(s"Delete is failed. customer Id is not available $customerId"))
//    }
//
//  override def customerById(customerId: Int): ZIO[Any, CustomerError.NotFound, Customer] =
//
//      ZIO.fromOption(customerDetails(customerId)).orElseFail(CustomerError.NotFound(s"customer not found for $customerId"))
//        .debug(s"customer found for $customerId")
//
//
//  private val customerDetails = (customerId :Int) =>  Database.customerList.find(customer=> customer.customerId.equals(customerId))

override def getAllCustomer(): ZIO[Any, CustomerError.NotFound, List[Customer] ] = (
  for {
    customers <- dynamoDbService
      .getAllItems(tableName = customersTableName)
      .flatMap(streamOfItems =>
        streamOfItems
          .map(item => itemToCustomer(Some(item)))
          .collect { case Some(customer) => customer }
          .runCollect
          .map(_.toList))
  } yield customers
  ).provide(dynamoDBExecutorLayer).orElseFail(CustomerError.NotFound("customerNotFound"))





private def itemToCustomer(item : Option[Item]): Option[Customer]= {
  item match {
    case Some(data) =>
      val id: Int =
        data.get[Int]("id").fold(error => 0, success => success.toInt)
      val firstName: String =
        data.get[String]("firstName").fold(error => "", success => success)
      val lastName: String =
        data.get[String]("lastName").fold(error => "", success => success)
      val address: String =
        data.get[String]("address").fold(error => "", success => success)
      val email: String =
        data.get[String]("email").fold(error => "", success => success)

      Some(Customer.from(id, firstName, lastName, address, email))

    case None => None
  }
}

}




