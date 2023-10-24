package com.gowtham.service.CustomerService

import com.gowtham.dynamodb.Database
import com.gowtham.exception.CustomerError
import com.gowtham.models.{Customer, Customers}
import zio._

case class CustomerServiceLive() extends CustomerService{
  override def createCustomer(customer: Customer): ZIO[Any, CustomerError.InvalidInput, Customers] = {
    Database.customerList.customers.find(customerDetails => customerDetails.customerId.equals(customer.customerId)) match {
      case None =>
        Database.customerList.customers += customer
        println(s"created customer inside the repo ${customer.customerId}")
        ZIO.succeed(Database.customerList)

      case Some(customerInfo) =>
        println(s"new customer is not added. Already exist same vaccinationId : ${customerInfo.customerId}")
          ZIO.fail(CustomerError.InvalidInput(s"Insert is failed. Vaccination Id is already available ${customerInfo.customerId}"))
    }
  }

  override def updateCustomer(customer: Customer): ZIO[Any, CustomerError.InvalidInput, Customers] = {
    Database.customerList.customers.find(customerDetails => customerDetails.customerId.equals(customer.customerId)) match {

      case Some(customerInfo) =>
        Database.customerList.customers.update(Database.customerList.customers.indexOf(customerInfo), customer)
        println(s"Update customer for customer : ${customerInfo.customerId}")
          ZIO.succeed(Database.customerList)

      case _ =>
        Database.customerList.customers += customer
        println(s"Update customer for customerId : ${customer.customerId}")
          ZIO.fail(CustomerError.InvalidInput(s"Update is failed. customer Id is not available ${customer.customerId}"))

    }
  }

  override def deleteCustomer(customerId: Int): ZIO[Any, CustomerError.InvalidInput, Unit] =
    Database.customerList.customers.find(customerDetails => customerDetails.customerId.equals()) match {
      case Some(customerInfo) =>
        Database.customerList.customers  -= customerInfo
        println(s"Deleted customer  : $customerId")
          ZIO.succeed(())
      case _ =>
        println(s"new customer couldn't be deleted. customer does not exist : $customerId")
          ZIO.fail(CustomerError.InvalidInput(s"Delete is failed. customer Id is not available $customerId"))
    }

  override def customerById(customerId: Int): ZIO[Any, CustomerError.NotFound, Customer] =

      ZIO.fromOption(customerDetails(customerId)).orElseFail(CustomerError.NotFound(s"customer not found for $customerId"))
        .debug(s"customer not found for $customerId")


  private val customerDetails = (customerId :Int) =>  Database.customerList.customers.find(customer=> customer.customerId.equals(customerId))
}
