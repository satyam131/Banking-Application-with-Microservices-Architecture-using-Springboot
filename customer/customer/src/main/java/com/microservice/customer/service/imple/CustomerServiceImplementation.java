package com.microservice.customer.service.imple;

import com.microservice.customer.entity.Customer;
import com.microservice.customer.exception.UserValidation;
import com.microservice.customer.repository.CustomerRepository;
import com.microservice.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class CustomerServiceImplementation implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer addCustomer(Customer customer) {

        if (customer.getName() == null || !customer.getName().matches("^[a-zA-Z ]+$")) {
            throw new UserValidation("Enter valid name");
        }
        if (customer.getEmail() == null || !customer.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
            throw new UserValidation("Enter valid email");
        }

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long customerId) {

        return customerRepository.findById(customerId).orElse(null);
    }

    @Override
    public Customer updateCustomer(Long customerId, Customer updatedCustomer) {
        if (updatedCustomer.getName() == null || !updatedCustomer.getName().matches("^[a-zA-Z ]+$")) {
            throw new UserValidation("Enter valid name");
        }
        if (updatedCustomer.getEmail() == null || !updatedCustomer.getEmail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$")) {
            throw new UserValidation("Enter valid email");
        }

        Customer existingCustomer = customerRepository.findById(customerId).orElse(null);
        if (existingCustomer != null) {
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            return customerRepository.save(existingCustomer);
        }
        return null;
    }


    @Override
    public void deleteCustomerAndAccount(Long customerId) {
        Customer existingCustomer = customerRepository.findById(customerId).orElse(null);
        if (existingCustomer != null) {
            customerRepository.delete(existingCustomer);
        }
    }

    @Override
    public Customer getCustomerFromAccount(Long customerId) {
        return customerRepository.findById(customerId).orElse(null);
    }
}
