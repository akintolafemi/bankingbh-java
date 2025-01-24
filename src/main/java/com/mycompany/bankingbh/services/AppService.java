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
import com.mycompany.bankingbh.utils.StandardResponse;
import com.mycompany.bankingbh.utils.Utils;
import java.math.BigDecimal;
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
                return ResponseManager.standardResponse(404, HttpStatus.NOT_FOUND, "Customer not found", customer);
            }

            // Check if current account already exists
            Account existingCurrentAccount = accountRepository.findFirstByCustomerIdAndAccountTypeAndDeletedFalse(
                    req.getCustomerId(), "current");
            if (existingCurrentAccount != null) {
                return ResponseManager.standardResponse(409, HttpStatus.CONFLICT, "Customer has an existing current account", existingCurrentAccount);
            }

            // Create new current account
            String accountNumber = Utils.generateAccountNumber();
            Account account = new Account();
            account.setCustomerId(req.getCustomerId());
            account.setAccountNumber(accountNumber);
            account.setAccountType("current");
            accountRepository.save(account);

            // Handle initial credit
            if (req.getInitialCredit() != null) {
                BigDecimal initialCredit = req.getInitialCredit();
                Account customerSavingsAccount = accountRepository.findFirstByCustomerIdAndAccountTypeAndDeletedFalse(
                        req.getCustomerId(), "savings");

                if (customerSavingsAccount != null && customerSavingsAccount.getAccountBalance().compareTo(initialCredit) > 0) {
                    // Deduct from savings
                    customerSavingsAccount.setAccountBalance(customerSavingsAccount.getAccountBalance().subtract(initialCredit));
                    accountRepository.save(customerSavingsAccount);

                    // Credit to current account
                    account.setAccountBalance(initialCredit);
                    accountRepository.save(account);

                    // Log the transaction
                    Transaction transaction = new Transaction();
                    transaction.setSource_account_number(customerSavingsAccount.getAccountNumber());
                    transaction.setDestination_account_number(accountNumber);
                    transaction.setAmount(initialCredit);
                    transactionRepository.save(transaction);
                }
            }

            return ResponseManager.standardResponse(200, HttpStatus.OK, "Current account created!", account);
        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown error occurred", e);
        }
    }

    public ResponseEntity fetchCustomerData(String customerId) {
        try {
            Customer customer = customerRepository.findByCustomerIdWithAccounts(customerId);
            if (customer == null) {
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
