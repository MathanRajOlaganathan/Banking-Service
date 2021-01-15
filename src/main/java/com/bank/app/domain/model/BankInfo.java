package com.bank.app.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 12/01/2021
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class BankInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bank_id")
    private Long id;
    private String branchName;
    private Integer branchCode;
    @OneToOne(cascade = CascadeType.ALL)
    private Address branchAddress;
}
