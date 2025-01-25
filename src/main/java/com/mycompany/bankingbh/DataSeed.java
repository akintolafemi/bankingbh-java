/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankingbh;
import com.mycompany.bankingbh.models.Account;
import com.mycompany.bankingbh.models.Customer;
import com.mycompany.bankingbh.repositories.AccountRepository;
import com.mycompany.bankingbh.repositories.CustomerRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author Makintola
 */

@Component
public class DataSeed implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    public DataSeed(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            Customer janeDoe = new Customer();
            janeDoe.setCustomerId("ad4501a3-233b-41d6-8d80-0e6e0a4ae2b4");
            janeDoe.setFullName("Jane Doe");
            janeDoe.setEmail("janedoe@gmail.com");
            janeDoe.setCreatedAt(LocalDateTime.now()); 

    //
            Account janeAccount = new Account();
            janeAccount.setAccountNumber("0011223344");
            janeAccount.setAccountBalance(BigDecimal.valueOf(5000.00));
            janeAccount.setAccountType("savings");
            janeAccount.setCustomer(janeDoe);
            janeAccount.setCreatedAt(LocalDateTime.now()); 
    //
            customerRepository.save(janeDoe);
            accountRepository.save(janeAccount);
    //
    //        // Seed Micheal Oluwafemi customer
            Customer michealOluwafemi = new Customer();
            michealOluwafemi.setCustomerId("639da415-bd0e-4807-a4e8-6979c1910c25");
            michealOluwafemi.setFullName("Micheal Oluwafemi");
            michealOluwafemi.setEmail("michealakintola106.pog@gmail.com");
            michealOluwafemi.setCreatedAt(LocalDateTime.now()); 
    //    //
            Account michealAccount = new Account();
            michealAccount.setAccountNumber("1020304050");
            michealAccount.setAccountBalance(BigDecimal.valueOf(4000.00));
            michealAccount.setAccountType("savings");
            michealAccount.setCustomer(michealOluwafemi);
            michealAccount.setCreatedAt(LocalDateTime.now());
    //
    //
            customerRepository.save(michealOluwafemi);
            accountRepository.save(michealAccount);

            System.out.println("Data seeded successfully!");
        } catch(Exception e) {
            System.out.println("e: " + e.toString());
        }
    }
}
