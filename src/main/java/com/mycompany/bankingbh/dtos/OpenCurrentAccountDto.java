/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankingbh.dtos;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import java.math.BigDecimal;

/**
 *
 * @author Makintola
 */
public class OpenCurrentAccountDto { //DTO to ensure request body matches expected types

    @ApiModelProperty(
        value = "Unique customer identifier",
        required = true,
        example = "c680e057-7e1f-470f-a482-e6404376b9c1"
    )
    @NotNull(message = "Customer ID is required")
    private String customerId;

    @ApiModelProperty(
        value = "Initial credit amount for the new account",
        example = "0.00",
        required = false
    )
    @DecimalMin(value = "0.00", inclusive = true, message = "Initial credit must be a positive value")
    @Digits(integer = 10, fraction = 2, message = "Initial credit should be a valid decimal with two decimal points")
    private BigDecimal initialCredit;

    // Getters and setters
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getInitialCredit() {
        return initialCredit;
    }

    public void setInitialCredit(BigDecimal initialCredit) {
        this.initialCredit = initialCredit;
    }
}