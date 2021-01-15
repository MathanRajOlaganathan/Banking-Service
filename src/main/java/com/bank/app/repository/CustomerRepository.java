package com.bank.app.repository;

import com.bank.app.domain.exception.NotFoundException;
import com.bank.app.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findById(Long id);

    default Customer getById(Long id) {
        Optional<Customer> optionalCustomer = findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new NotFoundException(Customer.class, id);
        }
        return optionalCustomer.get();
    }


    Optional<Customer> findByCustomerNumber(Long custNo);

}
