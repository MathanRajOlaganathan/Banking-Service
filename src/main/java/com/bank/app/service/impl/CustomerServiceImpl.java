package com.bank.app.service.impl;

import com.bank.app.domain.dto.CustomerRequest;
import com.bank.app.domain.dto.CustomerView;
import com.bank.app.domain.mapper.CustomerMapper;
import com.bank.app.domain.mapper.CustomerViewMapper;
import com.bank.app.domain.model.Customer;
import com.bank.app.repository.CustomerRepository;
import com.bank.app.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerViewMapper customerViewMapper;

    /**
     * Find a Customer
     *
     * @return
     */
    public CustomerView find(Long id) {
        return customerViewMapper.toCustView(customerRepository.getById(id));
    }

    /**
     * Find all Customers
     *
     * @return
     */
    public List<CustomerView> findAll() {
        return customerViewMapper.toCustView(customerRepository.findAll());
    }

    /**
     * Add Customer
     *
     * @param customerRequest
     * @return
     */
    public CustomerView addCustomer(CustomerRequest customerRequest) {
        if (customerRepository.findByCustomerNumber(customerRequest.getCustomerNumber()).isPresent()) {
            throw new ValidationException("Customer Already exists!");
        }
        Customer customer = customerMapper.addCustomer(customerRequest);
        customer.setCreateDateTime(LocalDate.now());
        customer = customerRepository.save(customer);
        log.debug("Customer created successfully");
        return customerViewMapper.toCustView(customer);
    }

    /**
     * Update Customer details
     *
     * @param customerRequest
     * @param id
     * @return
     */
    public CustomerView updateCustomer(CustomerRequest customerRequest, Long id) {
        Customer customer = customerRepository.getById(id);
        customerMapper.updateCustomer(customerRequest, customer);
        customerRepository.save(customer);
        log.debug("Customer details updated successfully");
        return customerViewMapper.toCustView(customer);

    }

    /**
     * Delete Customer
     *
     * @param id
     * @return
     */
    @Override
    public void deleteCustomer(Long id) {
        if (customerRepository.getById(id) != null) {
            customerRepository.deleteById(id);
            log.debug("Customer deleted successfully");
        }
    }
}


