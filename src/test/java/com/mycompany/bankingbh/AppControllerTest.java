/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.bankingbh;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 *
 * @author Makintola
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Value("${bearer.token}")
    private String BEARER_TOKEN;
    
    @Test
    public void testNullAuthorizationToken() throws Exception {
        mockMvc.perform(get("/api/v1/customer/639da415-bd0e-4807-a4e8-6979c1910c25"))
                .andExpect(status().isUnauthorized()); // Assert HTTP 401 status
    }
    
    @Test
    public void testInvalidAuthorizationToken() throws Exception {
        mockMvc.perform(get("/api/v1/customer/639da415-bd0e-4807-a4e8-6979c1910c25")
                .header("authorization", "Bearer token"))
                .andExpect(status().isUnauthorized()); // Assert HTTP 401 status
    }
    
    @Test
    public void testGetCustomer() throws Exception {
        mockMvc.perform(get("/api/v1/customer/639da415-bd0e-4807-a4e8-6979c1910c25")
                .header("Authorization", "Bearer " + BEARER_TOKEN))
                .andExpect(status().isOk()); // Assert HTTP 200 status
    }
    
    @Test
    public void testGetInvalidCustomer() throws Exception {
        mockMvc.perform(get("/api/v1/customer/639da415-bd0e-4807-a4e8-6979c1910c20")
                .header("Authorization", "Bearer " + BEARER_TOKEN))
                .andExpect(status().isNotFound()); // Assert HTTP 404 status

    }
    
    @Test
    public void testGetValidAccountNumber() throws Exception {
        mockMvc.perform(get("/api/v1/transactions/0011223344")
                .header("Authorization", "Bearer " + BEARER_TOKEN))
                .andExpect(status().isOk()); // Assert HTTP 200 status

    }
    
    @Test
    public void testGetInvalidAccountNumber() throws Exception {
        mockMvc.perform(get("/api/v1/transactions/0000000000")
                .header("Authorization", "Bearer " + BEARER_TOKEN))
                .andExpect(status().isNotFound()); // Assert HTTP 404 status

    }
    
    @Test
    public void testInvalidCustomerOpenCurrentAccount() throws Exception {
        String requestBody = "{\"customerId\": \"639da415-bd0e-4807-a4e8-6979c1910c20\", \"initialCredit\": 1000.0}";

        mockMvc.perform(post("/api/v1/open-current-account")
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isNotFound()); // Assert HTTP 404 status

    }
    
    @Test
    public void testNullCustomerOpenCurrentAccount() throws Exception {
        String requestBody = "{\"initialCredit\": 1000.0}";

        mockMvc.perform(post("/api/v1/open-current-account")
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest()); // Assert HTTP 400 status

    }
    
    @Test
    public void testInvalidInitialCreditOpenCurrentAccount() throws Exception {
        String requestBody = "{\"customerId\": \"639da415-bd0e-4807-a4e8-6979c1910c25\", \"initialCredit\": -1000.0}";

        mockMvc.perform(post("/api/v1/open-current-account")
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest()); // Assert HTTP 400 status

    }
}