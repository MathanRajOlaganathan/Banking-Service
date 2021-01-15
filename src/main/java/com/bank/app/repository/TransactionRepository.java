package com.bank.app.repository;

import com.bank.app.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public Optional<List<Transaction>> findByAccountNumber(Long accountNumber);

    @Query(value = "SELECT * FROM  (SELECT * FROM transaction A where A.date BETWEEN :startDate AND :endDate) b where b.account_number =:accountNumber",nativeQuery = true)
    public Optional<List<Transaction>> findAllByAccountNumberAndTimeRange(@Param("accountNumber") Long accountNumber, @Param("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate startDate,
                                                                          @Param("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate endDate);
}
