package com.bank.app.service;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;
import static java.time.temporal.ChronoUnit.YEARS;

/**
 * @author Mathan Raj O
 * @version 1.0
 * @since 15/01/2021
 */
public class Mian {

    public static void main(String[] args) {
        long daysBetween= YEARS.between(LocalDate.now().plusYears(1), LocalDate.now());
        long year = LocalDate.now().plusYears(1).lengthOfYear();
        long creatdDate = (LocalDate.now()).getDayOfYear();
        System.out.println((year%creatdDate==0));
    }
}

