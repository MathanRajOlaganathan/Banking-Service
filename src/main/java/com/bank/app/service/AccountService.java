package com.bank.app.service;

import com.bank.app.domain.dto.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 14/01/2021
 */
public interface AccountService {

    public Double getAccountBalance(Long accountNumber);

    public AccountView createAccount(AccountRequest accountRequest);

    public CustomerAccountView linkCustomerWithAccount(CustomerAccountRequest customerAccountRequest);

    public String transferMoney(TransferRequest transferRequest);

    public List<TransactionView> findTransactionsByAccountNumber(Long accountNumber, LocalDate startDate, LocalDate endDate);
}
