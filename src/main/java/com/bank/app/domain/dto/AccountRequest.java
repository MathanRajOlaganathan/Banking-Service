package com.bank.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountRequest {

    @NotNull
    private BankRequest bankInformation;
    @NotNull
    private String accountType;
    @NotNull
    private Double accountBalance;
}
