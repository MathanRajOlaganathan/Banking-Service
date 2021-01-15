package com.bank.app.domain.mapper;

import com.bank.app.domain.dto.AccountRequest;
import com.bank.app.domain.dto.CustomerAccountRequest;
import com.bank.app.domain.model.Account;
import com.bank.app.domain.model.CustomerAccount;
import com.bank.app.domain.model.Transaction;
import org.mapstruct.Mapper;

import java.time.LocalDate;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {
    public Account addAccount(AccountRequest accountRequest);

    public CustomerAccount linkAccount(CustomerAccountRequest customerAccountRequest);

    public Transaction createTransaction(Long accountNumber, Double amount, String type, LocalDate date);

}
