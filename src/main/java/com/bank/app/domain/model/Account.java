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
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "acc_id")
    private Long id;
    private Long accountNumber;
    @OneToOne(cascade = CascadeType.ALL)
    private BankInfo bankInformation;
    private AccountType accountType;
    private Double accountBalance;
    @Column(columnDefinition = "DATE")
    private LocalDate createdAt;
    @Column(columnDefinition = "DATE")
    private LocalDate modifiedAt;
    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = Math.round(accountBalance * 100.0) / 100.0;
    }


}
