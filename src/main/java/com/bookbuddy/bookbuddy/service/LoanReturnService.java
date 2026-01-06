package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.entity.BookCopy;
import com.bookbuddy.bookbuddy.entity.Loan;
import com.bookbuddy.bookbuddy.repository.BookCopyRepository;
import com.bookbuddy.bookbuddy.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanReturnService {

    private final LoanRepository loanRepository;
    private final BookCopyRepository bookCopyRepository;

    public LoanReturnService(LoanRepository loanRepository,
                             BookCopyRepository bookCopyRepository) {
        this.loanRepository = loanRepository;
        this.bookCopyRepository = bookCopyRepository;
    }

    public void returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new BusinessException("Imprumut inexistent"));

        if (loan.getReturnDate() != null) {
            throw new BusinessException("Imprumutul este deja returnat");
        }

        BookCopy copy = bookCopyRepository.findById(loan.getBookCopyId())
                .orElseThrow(() -> new BusinessException("Exemplar inexistent"));

        copy.setAvailable(true);
        bookCopyRepository.save(copy);

        loan.setReturnDate(LocalDate.now());
        loanRepository.save(loan);
    }
}
