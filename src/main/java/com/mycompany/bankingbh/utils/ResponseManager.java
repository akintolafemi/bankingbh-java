/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankingbh.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Makintola
 */


public class ResponseManager {
    
    // Standard response
    public static ResponseEntity<StandardResponse> standardResponse(int code, HttpStatus status, String message, Object data) {
        StandardResponse response = new StandardResponse();
        response.setCode(code);
        response.setStatus(status); 
        response.setMessage(message);
        response.setData(data);
        
        return new ResponseEntity<>(response, status);  // Return ResponseEntity with status
    }

    // Paginated response
    public static ResponseEntity<PaginatedResponse> paginatedResponse(
            int code, HttpStatus status, String message, Object data, Meta meta, Object extradata) {
        PaginatedResponse response = new PaginatedResponse();
        response.setCode(code);
        response.setStatus(status);  
        response.setMessage(message);
        response.setData(data);
        response.setMeta(meta);
        response.setExtradata(extradata);
        
        return new ResponseEntity<>(response, status);  // Return ResponseEntity with status
    }
}
