package com.bank.app.domain.mapper;

import com.bank.app.domain.dto.AccountView;
import com.bank.app.domain.dto.CustomerAccountView;
import com.bank.app.domain.dto.TransactionView;
import com.bank.app.domain.model.Account;
import com.bank.app.domain.model.CustomerAccount;
import com.bank.app.domain.model.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Mapper(componentModel = "spring")
public interface AccountViewMapper {

    public AccountView toAccView(Account account);

    public CustomerAccountView toCustAccView(CustomerAccount customerAccount);

    public List<TransactionView> toTransactionView(List<Transaction> transaction);

}
