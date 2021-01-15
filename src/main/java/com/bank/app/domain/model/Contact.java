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

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Contact {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cont_id")
    private Long id;
    private String emailId;
    private String contactNo;
}
