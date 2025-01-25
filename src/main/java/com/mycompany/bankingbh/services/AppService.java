/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankingbh.services;

import com.mycompany.bankingbh.models.Account;
import com.mycompany.bankingbh.dtos.OpenCurrentAccountDto;
import com.mycompany.bankingbh.models.Customer;
import com.mycompany.bankingbh.models.Transaction;
import com.mycompany.bankingbh.repositories.AccountRepository;
import com.mycompany.bankingbh.repositories.CustomerRepository;
import com.mycompany.bankingbh.repositories.TransactionRepository;
import com.mycompany.bankingbh.utils.ResponseManager;
import com.mycompany.bankingbh.utils.Utils;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Makintola
 */
@Service
public class AppService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public ResponseEntity openCustomerCurrentAccount(OpenCurrentAccountDto req) {
        try {
            // Check if customer exists
            Customer customer = customerRepository.findByCustomerId(req.getCustomerId());
            if (customer == null) {
                // return error message if customer does not exist
                return ResponseManager.standardResponse(404, HttpStatus.NOT_FOUND, "Customer not found", customer);
            }

            // Check if current account already exists for customer to disable multiple current accounts for a customer
            Account existingCurrentAccount = accountRepository.findFirstByCustomer_CustomerIdAndAccountTypeAndDeletedFalse(
                    req.getCustomerId(), "current");
            if (existingCurrentAccount != null) {
                // return conflict error message if customer already has a current account
                return ResponseManager.standardResponse(409, HttpStatus.CONFLICT, "Customer has an existing current account", existingCurrentAccount);
            }

            // Create new current account
            String accountNumber = Utils.generateAccountNumber();
            Account account = new Account();
            account.setCustomer(customer);
            account.setAccountNumber(accountNumber);
            account.setAccountType("current");
            account.setCreatedAt(LocalDateTime.now());
            account.setAccountBalance(new BigDecimal("0.00"));
            accountRepository.save(account);

            // Handle initial credit
            if (req.getInitialCredit() != null) {
                BigDecimal initialCredit = req.getInitialCredit();
                Account customerSavingsAccount = accountRepository.findFirstByCustomer_CustomerIdAndAccountTypeAndDeletedFalse(
                        req.getCustomerId(), "savings");

                if (customerSavingsAccount != null && customerSavingsAccount.getAccountBalance().compareTo(initialCredit) > 0) {
                    // Deduct from savings account
                    customerSavingsAccount.setAccountBalance(customerSavingsAccount.getAccountBalance().subtract(initialCredit));
                    accountRepository.save(customerSavingsAccount);

                    // Credit to new current account
                    account.setAccountBalance(initialCredit);

                    // Log the transaction
                    Transaction transaction = new Transaction();
//                    transaction.setSourceAccount(customerSavingsAccount);
//                    transaction.setDestinationAccount(account);
                    transaction.setSource_account_number(customerSavingsAccount.getAccountNumber()); //savings account as source
                    transaction.setDestination_account_number(accountNumber); //current account as destination
                    transaction.setAmount(initialCredit);
                    transaction.setCreatedAt(LocalDateTime.now());
                    transactionRepository.save(transaction);
                }
            }

            return ResponseManager.standardResponse(200, HttpStatus.OK, "Current account created!", account);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("ee: " + e.toString());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error occurred", e);
        }
    }

    public ResponseEntity fetchCustomerData(String customerId) {
        try {
            Customer customer = customerRepository.findByCustomerIdWithAccounts(customerId);
            if (customer == null) {
                // return error message if customer does not exist
                return ResponseManager.standardResponse(404, HttpStatus.NOT_FOUND, "Customer not found", customer);
            }
            return ResponseManager.standardResponse(200, HttpStatus.OK, "Customer data fetched successfully!", customer);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error occurred", e);
        }
    }

    public ResponseEntity fetchAccountTransactions(String accountNumber) {
        try {
            Account account = accountRepository.findFirstByAccountNumberAndDeletedFalse(accountNumber);
            if (account == null)
                return ResponseManager.standardResponse(404, HttpStatus.NOT_FOUND, "Account not found", null);
            
            List<Transaction> transactions = transactionRepository.findByAccountNumber(accountNumber);
            return ResponseManager.standardResponse(200, HttpStatus.OK, 
                    "Account transactions fetched successfully!", transactions);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error occurred", e);
        }
    }
}
