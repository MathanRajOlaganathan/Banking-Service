package com.bank.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressRequest {
    @NotBlank
    private String address1;
    private String address2;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String postcode;
    @NotBlank
    private String country;
}
