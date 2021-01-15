package com.bank.app.domain.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Data
public class AccountView {
    private Long accountNumber;
    private BankRequest bankInformation;
    private String accountType;
    private Double accountBalance;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
}
