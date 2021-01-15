package com.bank.app.repository;

import com.bank.app.domain.model.CustomerAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Repository
public interface CustomerAccountRepository extends JpaRepository<CustomerAccount, Long> {
}
