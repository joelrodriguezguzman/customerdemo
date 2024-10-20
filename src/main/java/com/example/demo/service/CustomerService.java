package com.example.demo.service;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ComponentScan(basePackages = {"com.example.demo.service"})
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow();
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer foundCustomer = getCustomerById(id);
        foundCustomer.setName(customer.getName());
        foundCustomer.setMname(customer.getMname());
        foundCustomer.setLname(customer.getLname());
        foundCustomer.setEmail(customer.getEmail());
        foundCustomer.setPhone(customer.getPhone());
        return customerRepository.save(foundCustomer);
    }

    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Customer not found");
        }
    }
}
