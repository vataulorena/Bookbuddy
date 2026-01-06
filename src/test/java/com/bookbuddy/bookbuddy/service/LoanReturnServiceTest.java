package com.bookbuddy.bookbuddy.service;

import com.bookbuddy.bookbuddy.entity.BookCopy;
import com.bookbuddy.bookbuddy.entity.Loan;
import com.bookbuddy.bookbuddy.repository.BookCopyRepository;
import com.bookbuddy.bookbuddy.repository.LoanRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanReturnServiceTest {

    private final LoanRepository loanRepository = mock(LoanRepository.class);
    private final BookCopyRepository bookCopyRepository = mock(BookCopyRepository.class);

    private final LoanReturnService service = new LoanReturnService(loanRepository, bookCopyRepository);

    @Test
    void returnLoan_shouldThrow_whenAlreadyReturned() {
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setBookCopyId(10L);
        loan.setReturnDate(LocalDate.now());

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

        BusinessException ex = assertThrows(BusinessException.class, () -> service.returnLoan(1L));
        assertEquals("Imprumutul este deja returnat", ex.getMessage());

        verify(bookCopyRepository, never()).save(any());
    }

    @Test
    void returnLoan_shouldMarkCopyAvailable_andSetReturnDate() {
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setBookCopyId(10L);
        loan.setReturnDate(null);

        BookCopy copy = new BookCopy();
        copy.setId(10L);
        copy.setAvailable(false);

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(bookCopyRepository.findById(10L)).thenReturn(Optional.of(copy));

        service.returnLoan(1L);

        assertTrue(copy.isAvailable());
        verify(bookCopyRepository).save(copy);

        assertNotNull(loan.getReturnDate());
        verify(loanRepository).save(loan);
    }
}
