package com.bank.app.service.impl;

import com.bank.app.domain.dto.*;
import com.bank.app.domain.exception.NotFoundException;
import com.bank.app.domain.mapper.AccountMapper;
import com.bank.app.domain.mapper.AccountViewMapper;
import com.bank.app.domain.model.Account;
import com.bank.app.domain.model.CustomerAccount;
import com.bank.app.domain.model.Transaction;
import com.bank.app.repository.AccountRepository;
import com.bank.app.repository.CustomerAccountRepository;
import com.bank.app.repository.CustomerRepository;
import com.bank.app.repository.TransactionRepository;
import com.bank.app.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountViewMapper accountViewMapper;
    private final AccountMapper accountMapper;
    private final CustomerAccountRepository customerAccountRepository;
    private final TransactionRepository transactionRepository;

    private static long nextAccountNumber = 112289;

    /**
     * Find account balance by account number
     *
     * @param accountNumber
     * @return
     */
    public Double getAccountBalance(Long accountNumber) {
        Account account = accountRepository.getByAccNo(accountNumber);
        log.debug("Retrieved Account Balance");
        return account.getAccountBalance();
    }

    /**
     * Create a new Account
     *
     * @param accountRequest
     * @return
     */
    public AccountView createAccount(AccountRequest accountRequest) {
        Account account = accountMapper.addAccount(accountRequest);
        LocalDate currentDate = LocalDate.now();
        account.setAccountNumber(accountGen());
        account.setCreatedAt(currentDate);
        account.setModifiedAt(currentDate);
        account = accountRepository.save(account);
        log.debug("Account created successfully");
        return accountViewMapper.toAccView(account);
    }

    /**
     * Link customer to an account
     *
     * @param customerAccountRequest
     * @return
     */
    @Transactional
    public CustomerAccountView linkCustomerWithAccount(CustomerAccountRequest customerAccountRequest) {
        CustomerAccountView customerAccountView = null;
        if (customerRepository.findByCustomerNumber(customerAccountRequest.getCustomerNumber()).isPresent()) {
            if (accountRepository.getByAccNo(customerAccountRequest.getAccountNumber()) != null) {
                CustomerAccount customerAccount = accountMapper.linkAccount(customerAccountRequest);
                customerAccount = customerAccountRepository.save(customerAccount);
                customerAccountView = accountViewMapper.toCustAccView(customerAccount);
                log.debug("Customer linked with the requested account successfully");
            }
        } else {
            throw new NotFoundException("Customer not exist, please enter another customer number correctly .");
        }
        return customerAccountView;
    }

    /**
     * Transfer funds from one account to another for a specific customer
     *
     * @param transferRequest
     */
    @Transactional
    public String transferMoney(TransferRequest transferRequest) {
        List<Account> accountEntities = new ArrayList<>();
        customerRepository.findByCustomerNumber(transferRequest.getCustomerNumber())
                .orElseThrow(() -> new NotFoundException("Customer not found."));
        Account fromAccount = accountRepository.getByAccNo(transferRequest.getFromAccountNumber());
        Account toAccount = accountRepository.getByAccNo(transferRequest.getToAccountNumber());
        // check for sufficient funds before transferring
        if (fromAccount.getAccountBalance() < transferRequest.getTransferAmount()) {
            throw new ValidationException("Unable to transfer. Insufficient Funds.");
        } else {
            synchronized (this) {
                // update FROM ACCOUNT
                fromAccount.setAccountBalance(fromAccount.getAccountBalance() - transferRequest.getTransferAmount());
                fromAccount.setModifiedAt(LocalDate.now());
                accountEntities.add(fromAccount);
                // update TO ACCOUNT
                toAccount.setAccountBalance(toAccount.getAccountBalance() + transferRequest.getTransferAmount());
                toAccount.setModifiedAt(LocalDate.now());
                accountEntities.add(toAccount);
                accountRepository.saveAll(accountEntities);
                // Create transaction for FROM Account
                Transaction fromTransaction = accountMapper.createTransaction(fromAccount.getAccountNumber(), transferRequest.getTransferAmount(), "DEBIT", LocalDate.now());
                transactionRepository.save(fromTransaction);
                // Create transaction for TO Account
                Transaction toTransaction = accountMapper.createTransaction(toAccount.getAccountNumber(), transferRequest.getTransferAmount(), "CREDIT", LocalDate.now());
                transactionRepository.save(toTransaction);
            }
            log.debug("Amount transferred successfully");
            return String.format("Success: Amount transferred");
        }
    }

    /**
     * At 00:00:00am every day, find the accounts which completed a year and
     * update the account balance with the annual interest rate
     */
    @Scheduled(cron = "0 0 0 * * ?")
    protected void updateAccountBalance() {
        log.debug("Update Account Balance");
        accountRepository.findAll().stream()
                .filter(acc -> accountOverByYear(acc.getCreatedAt()))
                .map(account -> calculateInterest(account))
                .forEach(account -> accountRepository.save(account));

    }

    /**
     * Check for 1 year completion of an account
     *
     * @param createdDate
     * @return
     */
    private static boolean accountOverByYear(LocalDate createdDate) {
        log.debug("Annual date of an account");
        long days = DAYS.between(createdDate, LocalDate.now());
        if (days >= 365) {
            return (days % 365 == 0);
        }
        return false;
    }

    /**
     * Calculate the updated amount after principal interest
     *
     * @param account
     * @return
     */
    private static Account calculateInterest(Account account) {
        log.debug("Calculate Interest");
        double result = account.getAccountBalance() + account.getAccountBalance() * 1 * 3.5 / 100.0;
        account.setAccountBalance(result);
        account.setModifiedAt(LocalDate.now());
        return account;
    }

    /**
     * Get all transactions for an account and a date range
     *
     * @param accountNumber
     * @returnn
     */
    public List<TransactionView> findTransactionsByAccountNumber(Long accountNumber, LocalDate startDate, LocalDate endDate) {
        log.debug("All Transactions by account number and date range");
        accountRepository.getByAccNo(accountNumber);
        Optional<List<Transaction>> transactions = transactionRepository.findAllByAccountNumberAndTimeRange(accountNumber, startDate, endDate);
        if (transactions.isPresent()) {
            return accountViewMapper.toTransactionView(transactions.get());
        } else {
            throw new NotFoundException("No transaction found for the given date range");
        }
    }


    /**
     * Generate the account number
     *
     * @return
     */
    private long accountGen() {
        return ++nextAccountNumber;
    }


}


