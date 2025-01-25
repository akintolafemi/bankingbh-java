/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankingbh.repositories;

import com.mycompany.bankingbh.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Makintola
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    //JPA handled method to fetch account by customer_id and account_type. Also check the record isn't deleted
    Account findFirstByCustomer_CustomerIdAndAccountTypeAndDeletedFalse(String customerId, String accountType);
    
    Account findFirstByAccountNumberAndDeletedFalse(String accountNumber);
}