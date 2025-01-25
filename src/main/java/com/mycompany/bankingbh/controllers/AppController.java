/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankingbh.controllers;

import com.mycompany.bankingbh.dtos.OpenCurrentAccountDto;
import com.mycompany.bankingbh.services.AppService;
import com.mycompany.bankingbh.utils.StandardResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Makintola
 */
@Api(tags = "BankingBH Accounts API")
@RestController
@RequestMapping("/api/v1") //apply this prefix to all routes in this controller
public class AppController {
    
    @Autowired
    private AppService appService;

    @ApiOperation(value = "Open a new current account", response = StandardResponse.class)
    @PostMapping("/open-current-account")
    public ResponseEntity openCustomerCurrentAccount(@Valid @RequestBody OpenCurrentAccountDto req) {
        return appService.openCustomerCurrentAccount(req);
    }

    @ApiOperation(value = "Fetch customer data", response = StandardResponse.class)
    @RequestMapping(value = "/customer/{customer_id}", method = RequestMethod.GET)
    public ResponseEntity fetchCustomerData(
            @ApiParam(value = "Customer ID", required = true, example = "c680e057-7e1f-470f-a482-e6404376b9c1")
            @PathVariable("customer_id") String customerId) {
        return appService.fetchCustomerData(customerId);
    }

    @ApiOperation(value = "Fetch account transactions", response = StandardResponse.class)
    @RequestMapping(value = "/transactions/{account_number}", method = RequestMethod.GET)
    public ResponseEntity fetchAccountTransactions(
            @ApiParam(value = "Account number", required = true, example = "00000000")
            @PathVariable("account_number") String accountNumber) {
        return appService.fetchAccountTransactions(accountNumber);
    }
}
