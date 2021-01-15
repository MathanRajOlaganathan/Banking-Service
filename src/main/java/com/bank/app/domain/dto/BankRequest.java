package com.bank.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
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
public class BankRequest {
    @NotBlank
    private String branchName;
    @NotNull
    private Integer branchCode;
    @NotNull
    private AddressRequest branchAddress;
}
