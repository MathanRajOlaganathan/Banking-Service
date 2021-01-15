package com.bank.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tx_id")
    private Long id;
    private Long accountNumber;
    @Column(columnDefinition = "DATE")
    private LocalDate date;
    private String type;
    private Double amount;
}
