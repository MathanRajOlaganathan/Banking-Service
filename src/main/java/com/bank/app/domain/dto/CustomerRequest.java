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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private Long customerNumber;
    @NotNull
    private AddressRequest customerAddress;
    @NotNull
    private ContactRequest contactDetails;

}
