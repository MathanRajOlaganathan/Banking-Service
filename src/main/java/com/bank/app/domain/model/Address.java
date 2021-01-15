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
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "add_id")
    private Long id;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String postcode;
    private String country;
}
