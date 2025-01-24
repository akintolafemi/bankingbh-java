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
    Account findFirstByCustomerIdAndAccountTypeAndDeletedFalse(String customerId, String accountType);
}