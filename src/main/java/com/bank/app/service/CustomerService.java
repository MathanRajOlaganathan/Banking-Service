package com.bank.app.service;

import com.bank.app.domain.dto.CustomerRequest;
import com.bank.app.domain.dto.CustomerView;

import java.util.List;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
public interface CustomerService {

    public List<CustomerView> findAll();

    public CustomerView addCustomer(CustomerRequest customerRequest);

    public CustomerView find(Long id);

    public CustomerView updateCustomer(CustomerRequest customerRequest, Long customerNumber);

    public void deleteCustomer(Long id) ;
}
