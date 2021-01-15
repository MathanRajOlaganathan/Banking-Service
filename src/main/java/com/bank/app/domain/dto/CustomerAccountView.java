package com.bank.app.domain.dto;

import lombok.Data;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Data
public class CustomerAccountView {
    private Long id;
    private Long accountNumber;
    private Long customerNumber;
}
