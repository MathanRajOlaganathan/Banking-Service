package com.bank.app.domain.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Data
public class CustomerView {

    private Long id;
    private String firstName;
    private String lastName;
    private Long customerNumber;
    private LocalDate createDateTime;
    private AddressRequest customerAddress;
    private ContactRequest contactDetails;
}
