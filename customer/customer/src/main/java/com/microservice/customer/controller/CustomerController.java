package com.microservice.customer.controller;

import com.microservice.customer.entity.Customer;
import com.microservice.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RestTemplate restTemplate;

    private static final String ACCOUNT_SERVICE_URL = "http://account-service/api/accounts/";

    @PostMapping
    public ResponseEntity<Object> addCustomer(@RequestBody Customer customer) {

        Customer customerData = customerService.getCustomerById(customer.getId());
        if (customerData != null) {
            String errorMessage = "Customer Already Present";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }

        Customer customerAdded = customerService.addCustomer(customer);

        return new ResponseEntity<>(customerAdded, HttpStatus.OK);
    }


    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }


    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getCustomerById(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            String errorMessage = "No customer found with this ID";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }


    @PutMapping("/{customerId}")
    public ResponseEntity<Object> updateCustomer(@PathVariable Long customerId, @RequestBody Customer updatedCustomer) {

        if (!customerId.equals(updatedCustomer.getId())) {
            String errorMessage = "Customer ID not match with updated Customer Data ID";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }

        Customer customer = customerService.updateCustomer(customerId, updatedCustomer);

        if (customer == null) {
            String errorMessage = "Customer Not Found, Please Enter Valid CustomerId";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }


    @DeleteMapping("/{customerId}")
    public ResponseEntity<Object> deleteCustomerAndAccount(@PathVariable Long customerId) {
        Customer customer = customerService.getCustomerById(customerId);

        if (customer == null) {
            return new ResponseEntity<>("Customer Not Found, Please Enter Valid Customer ID", HttpStatus.NOT_FOUND);
        }


        try {
            Map<String, Long> parameter = new HashMap<>();
            parameter.put("customerId", customerId);
            restTemplate.delete(ACCOUNT_SERVICE_URL + "/{customerId}", parameter);
        } catch (HttpClientErrorException excep) {
            System.out.println(excep.getMessage());
            String errorResponse = "Account Not found for the customer";
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        customerService.deleteCustomerAndAccount(customerId);

        String customerAccountDeleteStatus = "Customer and Account Deleted";
        return new ResponseEntity<>(customerAccountDeleteStatus, HttpStatus.OK);

    }


    @GetMapping("/getAccountCustomer/{customerId}")
    public Customer getCustomerFromAccount(@PathVariable Long customerId) {
        return customerService.getCustomerFromAccount(customerId);
    }
}
