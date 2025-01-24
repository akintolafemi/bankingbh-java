/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankingbh.utils;

import java.util.Random;

/**
 *
 * @author Makintola
 */
public class Utils {
    public static String generateAccountNumber() {
        long min = 1000000000L; // Minimum 10-digit number (1 followed by 9 zeros)
        long max = 9999999999L; // Maximum 10-digit number (9 nines)

        Random random = new Random();
        long accountNumber = min + (long) (random.nextDouble() * (max - min + 1));

        return String.valueOf(accountNumber); // Convert the number to a string and return it
    }
}
