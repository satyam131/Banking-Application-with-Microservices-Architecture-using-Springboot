package com.microservice.account.controller;

import com.microservice.account.entity.Account;
import com.microservice.account.entity.AllDetail;
import com.microservice.account.entity.Customer;
import com.microservice.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private RestTemplate restTemplate;


    private static final String CUSTOMER_SERVICE_URL = "http://customer-service/api/customers/";

    @PostMapping("/addMoney/{customerId}")
    public ResponseEntity<Object> addMoneyToAccount(@PathVariable Long customerId, @RequestParam Double amount) {
        if (amount < 0) {
            String errorMessage = "Please Enter valid Amount!";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }

        Account account = accountService.addMoneyToAccount(customerId, amount);

        if (account == null) {
            String errorMessage = "No Customer found, Please enter valid customerId";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account, HttpStatus.OK);
    }


    @PostMapping("/withdrawMoney/{customerId}")
    public ResponseEntity<Object> withdrawMoneyFromAccount(@PathVariable Long customerId, @RequestParam Double amount) {

        Customer isCustomerPresent = restTemplate.getForObject(CUSTOMER_SERVICE_URL + "getAccountCustomer/" + customerId, Customer.class);

        if (isCustomerPresent == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No Customer Found, Please Enter Valid CustomerId");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Account isAccountPresent = accountService.getAccountDetails(customerId);

        if (isAccountPresent == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No Account found for this Customer");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Account result = accountService.withdrawMoneyFromAccount(customerId, amount);

        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Insufficient balance");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{customerId}")
    public ResponseEntity<Object> getAccountDetails(@PathVariable Long customerId) {

        Customer CustomerPresent = restTemplate.getForObject(CUSTOMER_SERVICE_URL + "getAccountCustomer/" + customerId, Customer.class);

        if (CustomerPresent == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No Customer Found, Please Enter Valid CustomerId");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Account AccountPresent = accountService.getAccountDetails(customerId);

        if (AccountPresent == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No Account found for this Customer");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        AllDetail allDetails = new AllDetail();
        allDetails.setCustomerDetail(CustomerPresent);
        allDetails.setAccountDetail(AccountPresent);

        return new ResponseEntity<>(allDetails, HttpStatus.OK);
    }


    @DeleteMapping("/{customerId}")
    public ResponseEntity<Object> deleteAccount(@PathVariable Long customerId) {

        Customer isCustomerPresent = restTemplate.getForObject(CUSTOMER_SERVICE_URL + "getAccountCustomer/" + customerId, Customer.class);

        if (isCustomerPresent == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "No Customer Found, Please Enter Valid CustomerId");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        Account account = accountService.deleteAccount(customerId);

        if (account != null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("Success", "Account Deleted");
            return new ResponseEntity<>(errorResponse, HttpStatus.OK);
        }

        return new ResponseEntity<>("No Account Found for this Customer", HttpStatus.NOT_FOUND);
    }
}
