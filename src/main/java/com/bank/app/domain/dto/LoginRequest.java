package com.bank.app.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@Data
public class LoginRequest {

    @NotBlank
    @Email
    private String username;
    @NotBlank
    private String password;
}
