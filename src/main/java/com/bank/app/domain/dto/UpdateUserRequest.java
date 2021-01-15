package com.bank.app.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@Data
public class UpdateUserRequest {
    @NotBlank
    private String fullName;
    @NotNull
    private Set<String> roles;
}
