package com.bank.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenResponse {

    private String token;
}
