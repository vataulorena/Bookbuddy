package com.bookbuddy.bookbuddy.controller;

import com.bookbuddy.bookbuddy.dto.loan.CreateLoanRequest;
import com.bookbuddy.bookbuddy.service.LoanCreateService;
import com.bookbuddy.bookbuddy.service.LoanReturnService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanCreateService loanCreateService;
    private final LoanReturnService loanReturnService;

    public LoanController(LoanCreateService loanCreateService, LoanReturnService loanReturnService) {
        this.loanCreateService = loanCreateService;
        this.loanReturnService = loanReturnService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createLoan(@Valid @RequestBody CreateLoanRequest request) {
        loanCreateService.create(request);
    }

    @PostMapping("/{loanId}/return")
    @ResponseStatus(HttpStatus.OK)
    public void returnLoan(@PathVariable Long loanId) {
        loanReturnService.returnLoan(loanId);
    }
}
