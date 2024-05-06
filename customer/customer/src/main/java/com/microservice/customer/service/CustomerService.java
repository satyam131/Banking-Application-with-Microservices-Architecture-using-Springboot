package com.microservice.customer.service;

import com.microservice.customer.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer addCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerById(Long customerId);

    Customer updateCustomer(Long customerId, Customer updatedCustomer);

    void deleteCustomerAndAccount(Long customerId);

    Customer getCustomerFromAccount(Long customerId);
}
