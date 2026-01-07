package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.dto.loan.CreateLoanRequest;
import com.bookbuddy.bookbuddy.dto.loan.LoanResponse;
import com.bookbuddy.bookbuddy.entity.BookCopy;
import com.bookbuddy.bookbuddy.entity.Loan;
import com.bookbuddy.bookbuddy.entity.User;
import com.bookbuddy.bookbuddy.repository.BookCopyRepository;
import com.bookbuddy.bookbuddy.repository.LoanRepository;
import com.bookbuddy.bookbuddy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanCreateService {

    private final UserRepository userRepository;
    private final BookCopyRepository bookCopyRepository;
    private final LoanRepository loanRepository;

    public LoanCreateService(UserRepository userRepository,
                             BookCopyRepository bookCopyRepository,
                             LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.loanRepository = loanRepository;
    }

    public LoanResponse create(CreateLoanRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new BusinessException("User inexistent"));

        BookCopy copy = bookCopyRepository.findById(request.bookCopyId())
                .orElseThrow(() -> new BusinessException("Exemplar inexistent"));

        if (!copy.isAvailable()) {
            throw new BusinessException("Exemplarul nu este disponibil");
        }

        copy.setAvailable(false);
        bookCopyRepository.save(copy);

        Loan loan = new Loan();
        loan.setUser(user);
        loan.setBookCopyId(copy.getId());
        loan.setStartDate(LocalDate.now());
        loan.setDueDate(request.dueDate());
        loan.setReturnDate(null);

        Loan saved = loanRepository.save(loan);

        return new LoanResponse(
                saved.getId(),
                user.getId(),
                copy.getId(),
                saved.getStartDate(),
                saved.getDueDate(),
                saved.getReturnDate()
        );
    }
}
