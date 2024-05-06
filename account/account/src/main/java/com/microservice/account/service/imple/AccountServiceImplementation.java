package com.microservice.account.service.imple;

import com.microservice.account.entity.Account;
import com.microservice.account.entity.Customer;
import com.microservice.account.repository.AccountRepository;
import com.microservice.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountServiceImplementation implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RestTemplate restTemplate;

    private static final String CUSTOMER_SERVICE_URL = "http://customer-service/api/customers/";


    @Override
    public Account addMoneyToAccount(Long customerId, Double amount) {
        Customer customer = restTemplate.getForObject(CUSTOMER_SERVICE_URL + "getAccountCustomer/" + customerId, Customer.class);

        if (customer != null) {
            Account account = getOrCreateAccountForCustomer(customer);
            account.setBalance(account.getBalance() + amount);
            return accountRepository.save(account);
        }
        return null;
    }


    @Override
    public Account withdrawMoneyFromAccount(Long customerId, Double amount) {
        Customer customer = restTemplate.getForObject(CUSTOMER_SERVICE_URL + "getAccountCustomer/" + customerId, Customer.class);

        if (customer != null) {
            Account account = getOrCreateAccountForCustomer(customer);
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);
                return accountRepository.save(account);
            }
        }
        return null;
    }

    @Override
    public Account getAccountDetails(Long customerId) {
        Customer customer = restTemplate.getForObject(CUSTOMER_SERVICE_URL + customerId, Customer.class);

        if (customer != null) {
            return getAccountDetailsForCustomer(customer);
        }
        return null;
    }

    @Override
    public Account deleteAccount(Long customerId) {
        Account account = accountRepository.findByCustomerId(customerId);
        if (account != null) {
            accountRepository.delete(account);
            return account;
        }
        return null;
    }

    private Account getOrCreateAccountForCustomer(Customer customer) {
        Account account = accountRepository.findByCustomerId(customer.getId());
        if (account == null) {
            account = new Account();
            account.setCustomerId(customer.getId());
            account.setBalance(0.0);
        }
        return account;
    }

    private Account getAccountDetailsForCustomer(Customer customer) {
        return accountRepository.findByCustomerId(customer.getId());
    }
}
