package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.loan.CreateLoanRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createLoan(@Valid @RequestBody CreateLoanRequest request) {

    }

    @PostMapping("/{loanId}/return")
    @ResponseStatus(HttpStatus.OK)
    public void returnLoan(@PathVariable Long loanId) {

    }
}
