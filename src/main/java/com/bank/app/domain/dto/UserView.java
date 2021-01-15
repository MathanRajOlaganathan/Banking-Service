package com.bank.app.domain.dto;

import com.bank.app.domain.model.Role;
import lombok.Data;

import java.util.Set;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@Data
public class UserView {
    private Long id;
    private String username;
    private String fullName;
    private Set<String> roles;
}
