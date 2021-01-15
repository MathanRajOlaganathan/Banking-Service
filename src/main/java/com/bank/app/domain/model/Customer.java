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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cust_id")
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private Long customerNumber;
    private LocalDate createDateTime;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address customerAddress;
    @OneToOne(cascade = CascadeType.ALL)
    private Contact contactDetails;
}
