package com.bank.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerAccountRequest {

    @NotNull
    private Long accountNumber;
    @NotNull
    private Long customerNumber;
}
