package com.bank.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 13/01/2021
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_acc_id")
    private Long id;
    private Long accountNumber;
    private Long customerNumber;
}
