/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bankingbh;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author Makintola
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.mycompany.bankingbh")
public class Bankingbh {
    public static void main(String[] args) {
        SpringApplication.run(Bankingbh.class, args);
    }
}