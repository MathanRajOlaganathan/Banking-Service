package com.bank.app.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 14/01/2021
 */
@Getter
@Setter
public class TransactionView {
    private Long accountNumber;
    private LocalDate date;
    private String type;
    private Double amount;
}
