package com.bank.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
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
public class ContactRequest {
    @NotBlank
    @Email
    private String emailId;
    @NotBlank
    private String contactNo;
}
