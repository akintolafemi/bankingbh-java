/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bankingbh.config;

import com.mycompany.bankingbh.utils.ResponseManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author Makintola
 */
@ControllerAdvice
public class ValidationExceptionHandler {

    // Request body error validation message handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationExceptions(MethodArgumentNotValidException ex) {
        //return the first error handled
        return ResponseManager.standardResponse(400, HttpStatus.BAD_REQUEST, ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage(), null);
    }
}