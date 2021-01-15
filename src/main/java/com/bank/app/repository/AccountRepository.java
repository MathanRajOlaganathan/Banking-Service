package com.bank.app.repository;

import com.bank.app.domain.exception.NotFoundException;
import com.bank.app.domain.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(Long accountNumber);

    default Account getByAccNo(Long no) {
        return findByAccountNumber(no).orElseThrow(() -> new NotFoundException(Account.class, no));
    }

}
