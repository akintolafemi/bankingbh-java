/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankingbh.repositories;

import com.mycompany.bankingbh.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Makintola
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCustomerId(String customerId);
    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.accounts WHERE c.customerId = :customerId")
    Customer findByCustomerIdWithAccounts(@Param("customerId") String customerId);
}